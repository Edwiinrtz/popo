package xyz.edwinpalacios.po_po.viewmodel

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.database
import com.google.firebase.messaging.FirebaseMessaging
import android.R
import android.widget.Toast
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import xyz.edwinpalacios.po_po.model.Group
import xyz.edwinpalacios.po_po.model.MessageNotification
import xyz.edwinpalacios.po_po.model.User
import xyz.edwinpalacios.po_po.model.services.RetrofitService
import java.time.LocalDate

class MainViewModel(private val auth: FirebaseAuth) : ViewModel() {
    val database = Firebase.database.reference
    private val user: MutableLiveData<User> = MutableLiveData()
    private val group: MutableLiveData<Group> = MutableLiveData()
    private val retrofit = RetrofitService().apiService



    fun login(username: String, groupId: String, action: () -> Unit) {

        val email = "$username@popo.com"
        var actualGroupId = groupId
        if (groupId.isNullOrBlank()) {
            actualGroupId = hashCode().toString()
        }


        auth.signInWithEmailAndPassword(email, username + actualGroupId)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var authUser = auth.currentUser

                    val groupId = authUser?.displayName?.split("_")?.last()?:""
                    val actualUserName = authUser?.displayName?.split("_")?.first()?:""
                    updateTokenId(groupId.trim(), actualUserName.trim())
                    action()
                } else {
                    // If sign in fails, display a message to the user.

                    auth.createUserWithEmailAndPassword(email, username + actualGroupId)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("singin", "createUserWithEmail:success")
                                val user = auth.currentUser

                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(username + "_" + actualGroupId)
                                    .build()

                                user?.updateProfile(profileUpdates)

                                val newUser = User(
                                    name = username,
                                    kks = mutableMapOf(),
                                    profileUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                                    totalKks = 0
                                )

                                database.child("groups").child(actualGroupId).get().addOnSuccessListener {
                                    val nGroup: Group? = it.getValue(Group::class.java)

                                    if(nGroup!=null){
                                        nGroup?.members?.add(newUser)
                                        database.child("groups").child(actualGroupId).setValue(nGroup).addOnCompleteListener{
                                            if(task.isSuccessful){

                                                action()
                                            }
                                        }
                                        val message = "$username se unió al club de las kks."
                                        sendMessage("Nuevo miembro", message)

                                    }else{
                                        val newGroup = Group(id = actualGroupId, mutableListOf(newUser), 0)

                                        database.child("groups").child(actualGroupId).setValue(newGroup).addOnCompleteListener{
                                            action()
                                        }

                                    }
                                    updateTokenId(actualGroupId, username)


                                    Log.d("TOPIC", "TC-$actualGroupId")
                                    Firebase.messaging.subscribeToTopic("TC-$actualGroupId")
                                        .addOnCompleteListener { task ->
                                            var msg = "Subscribed"
                                            if (!task.isSuccessful) {
                                                msg = task.exception.toString()
                                            }
                                            Log.d("TOPIC", msg)
                                        }





                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("singig", "createUserWithEmail:failure", task.exception)
                            }
                        }

                }
            }


    }

    fun updateInfo(){

        val groupId = auth.currentUser?.displayName?.split("_")?.last()?:""
        val actualUserName = auth.currentUser?.displayName?.split("_")?.first()?:""

        database.child("groups").child(groupId).get().addOnSuccessListener {
            val nGroup: Group? = it.getValue(Group::class.java)

            if (nGroup != null) {
                group.postValue(nGroup)

                nGroup.members?.map { it ->
                    if(it.name == actualUserName){
                        user.postValue(it)
                    }
                }
            }else{
                throw Exception("se fue a la vrg")
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        //updateTokenId(groupId.trim(), actualUserName.trim())


    }

    fun addKk(){

        val actualDate = "2024-01-08"//LocalDate.now().toString()
        val actualUser = user.value
        val username = actualUser?.name

        val messagesList = listOf("¡$username  acaba de lanzar una kk al mix!",
            "$username  ha soltado la kk bomba.",
            "¡$username agregó una kk a la mezcla, cuidado!",
            "¡Alerta! $username  desató una kk en el universo.",
            "$username  ha dejado caer una kk, ¡literalmente!",
            "$username  ha soltado una kk épica, ¡prepárate!",
            "¡Atención! $username  acaba de kkificarlo todo.",
            "$username  añadió su toque personal: una kk.",
            "$username  ha dado el toque final con una kk maestra.")

        if(actualUser?.kks?.get(actualDate) != null){

            actualUser.kks?.set(actualDate, actualUser?.kks?.get(actualDate)!!.plus(1))
        }else{
            val nKKs = mutableMapOf( actualDate to 1)

            actualUser!!.kks = nKKs

        }

        actualUser.totalKks = actualUser.totalKks?.plus(1)

        group.value!!.members!!.map {
            if(it.name == user.value!!.name){
                user.postValue(actualUser)
            }
        }

        group?.value?.totalKks = group.value?.totalKks?.plus(1)
        database.child("groups").child(group?.value?.id!!).setValue(group.value).addOnCompleteListener { task ->

            if(task.isSuccessful){
                sendMessage("Una nueva kk ha sido agregada en tu grupo", messagesList.random())
            }

        }
    }
    fun getUser(): MutableLiveData<User> {
        return user
    }
    fun getGroup():MutableLiveData<Group> {
        return group
    }

    private fun updateTokenId(actualGroupId: String, user: String){

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("notification token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            Log.d("notification token", token.toString())

            database.child("tokens").child(actualGroupId).get().addOnSuccessListener{
                if(it.exists()){
                    val actualTokensList: MutableMap<String, String> =  it.value as MutableMap<String, String>
                    //val actualTokensMap:MutableMap<String, String> = it.getValue(MutableMap::class.java) as MutableMap<String, String>

                     if(actualTokensList[user] == null){
                         actualTokensList[user] = token
                         database.child("tokens").child(actualGroupId).setValue(actualTokensList).addOnCompleteListener{ nTask ->
                             if(nTask.isSuccessful){
                                 Log.i("Tokens", "Token registrado con exito")
                             }
                         }
                         /**/
                     }else{
                         val actualUserToken = actualTokensList[user]

                         if(actualUserToken != token){
                             actualTokensList[user] = token
                             database.child("tokens").child(actualGroupId).setValue(actualTokensList).addOnCompleteListener{ nTask ->
                                 if(nTask.isSuccessful){
                                     Log.i("Tokens", "Token actualizado con exito")
                                 }
                             }

                         }
                     }

                }else{
                    val tokens = mutableMapOf(user to token)
                    database.child("tokens").child(actualGroupId).setValue(tokens).addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Log.i("Tokens", "Token registrado con exito")
                        }
                    }

                }
            }


            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

        })
    }

    private fun sendMessage(title:String, message: String){


        val groupId = auth.currentUser?.displayName?.split("_")?.last()?:""

        database.child("tokens").child(groupId).get().addOnSuccessListener {
            if (it.exists()) {
                val actualTokensList: MutableMap<String, String> = it.value as MutableMap<String, String>

                val tokens = ArrayList(actualTokensList.values).toList()

                val nMessage = MessageNotification(title, message, tokens)
                CoroutineScope(Dispatchers.IO).launch {

                    val call = retrofit.sendNotification(nMessage)
                    call?.enqueue(object : retrofit2.Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                val apiResponse= response.body()
                                Log.i("Response retrofit", apiResponse.toString())
                            } else {
                                // Maneja la respuesta no exitosa
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            // Maneja el fallo de la llamada
                        }
                    })

                }
            }
        }
    }
}

