package com.example.test_ws

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.String
import kotlin.Throws
import kotlin.assert


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun Login_onClick(view: View) {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
        val client = OkHttpClient() //объект клиента

        //Строим FormBody, передаем параметры для отправки
        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        //задание ссылки для передачи параметров
        val request = Request.Builder()
            .url("http://cinema.areas.su/auth/login")
            .post(formBody)
            .build()

        Log.i("INFO", "Request was initialiazed")

        // Вызываем и устанавливаем функцию обратного вызова асинхронно
        client.newCall(request).enqueue(object : Callback {
            //действия при ошибке
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Test", "Test")
                Toast.makeText(applicationContext, "Данные введены неверно", Toast.LENGTH_LONG)
                    .show()
                e.printStackTrace() //--
            }
            //действия при успешном запросе
            @Throws(IOException::class) //--
            override fun onResponse(call: Call, response: Response) {
                try {
                    //если есть ошибка в отправке запроса
                    if (!response.isSuccessful) {
                        //Запускает указанное действие в потоке пользовательского интерфейса. Если текущий поток
                        // является потоком пользовательского интерфейса, то действие выполняется немедленно.
                        // Если текущий поток не является потоком пользовательского интерфейса, действие отправляется
                        // в очередь событий потока пользовательского интерфейса.
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Неверный логин/пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        throw IOException("Неверный логин/пароль")
                    }
                    //Выдает ошибку AssertionError, если значение равно false
                    assert(response.body() != null)

                    //class представляет неизменяемое значение объекта JSON (неупорядоченный набор из нуля или
                    // более пар имя/значение). Он также обеспечивает неизменяемый вид карты для сопоставлений имени/значения
                    // объекта JSON.
                    val json = JSONObject(response.body()!!.string())


                    GlobalVars.token = json.getInt("token")
                    Log.d("Token", String.valueOf(GlobalVars.token))
                    startActivity(Intent(applicationContext, Main::class.java))
                    finish()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}

/*
* //создание лаунчера
//новый метод
lifecycleScope.launchWhenCreated {
            delay(2000)
            val aloading = Intent(this@MainActivity, Activity_loading::class.java)
            finishActivity(0)
            startActivity(aloading)

        }
//старый метод
Handler().postDelayed({
            val Aloading = Intent(this, Activity_loading::class.java)
            startActivity(Aloading)
        }, 2000)

//переход на другой экран
fun SignUp_onClick (view: View){
        val SignUp = Intent(this, SignUpActivity::class.java)
        startActivity(SignUp)
    }

//валидация
if(lastNameText.text.isNotEmpty() and
            firstNameText.text.isNotEmpty() and
            middleNameText.text.isNotEmpty() and
                //!(birthdayDate.text.length == 0) and
            loginText.text.isNotEmpty() and
            passwordText.text.isNotEmpty() and
            passwordConfirmationText.text.isNotEmpty()
        ){
            if(loginText.text.toString().trim().matches(log.toRegex())){
                if (passwordConfirmationText.text.toString() == passwordText.text.toString()){
		}
		else passwordConfirmationText.error = "Пароли не совпадают"
            }
            else loginText.error = "Данные заполнены некоректно, логин должен соответствовать почте"
        }
        else {
            if(lastNameText.text.isEmpty()) lastNameText.error = "Поле не заполнено"
            if(firstNameText.text.isEmpty()) firstNameText.error = "Поле не заполнено"
            if (middleNameText.text.isEmpty()) middleNameText.error = "Поле не заполнено"
            //if (birthdayDate.text.length == 0) birthdayDate.error = "Поле не заполнено"
            if (loginText.text.isEmpty()) loginText.error = "Поле не заполнено"
            if (passwordText.text.isEmpty()) passwordText.error = "Поле не заполнено"
            if (passwordConfirmationText.text.isEmpty()) passwordConfirmationText.error = "Поле не заполнено"
        }

//Проверка почты
private val log = "[a-zA-Z0-9]+@[0-9a-z]+\\.+[a-z]{1,3}"

loginText.text.toString().trim().matches(log.toRegex())

//меню
class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

	//создание вот этого
        val bnv = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)

        bnv.setupWithNavController(navController)
    }
//при нажатии на фрагментах в меню
    fun ButtonClick(view: View) {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.action_secondFragment_to_thirdFragment)
    }
}

//авторизация
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.String
import kotlin.Throws
import kotlin.assert


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun Login_onClick(view: View) {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
        val client = OkHttpClient() //объект клиента

        //Строим FormBody, передаем параметры для отправки
        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        //задание ссылки для передачи параметров
        val request = Request.Builder()
            .url("http://cinema.areas.su/auth/login")
            .post(formBody)
            .build()

        Log.i("INFO", "Request was initialiazed")

        // Вызываем и устанавливаем функцию обратного вызова асинхронно
        client.newCall(request).enqueue(object : Callback {
            //действия при ошибке
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Test", "Test")
                Toast.makeText(applicationContext, "Данные введены неверно", Toast.LENGTH_LONG)
                    .show()
                e.printStackTrace() //--
            }
            //действия при успешном запросе
            @Throws(IOException::class) //--
            override fun onResponse(call: Call, response: Response) {
                try {
                    //если есть ошибка в отправке запроса
                    if (!response.isSuccessful) {
                        //Запускает указанное действие в потоке пользовательского интерфейса. Если текущий поток
                        // является потоком пользовательского интерфейса, то действие выполняется немедленно.
                        // Если текущий поток не является потоком пользовательского интерфейса, действие отправляется
                        // в очередь событий потока пользовательского интерфейса.
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Неверный логин/пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        throw IOException("Неверный логин/пароль")
                    }
                    //Выдает ошибку AssertionError, если значение равно false
                    assert(response.body() != null)

                    //class представляет неизменяемое значение объекта JSON (неупорядоченный набор из нуля или
                    // более пар имя/значение). Он также обеспечивает неизменяемый вид карты для сопоставлений имени/значения
                    // объекта JSON.
                    val json = JSONObject(response.body()!!.string())


                    GlobalVars.token = json.getInt("token")
                    Log.d("Token", String.valueOf(GlobalVars.token))
                    startActivity(Intent(applicationContext, Main::class.java))
                    finish()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}

//регистрация
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun SignUp_onClick(view: View) {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
        val firstName = findViewById<EditText>(R.id.firstNameEditText).text.toString()
        val lastName = findViewById<EditText>(R.id.lastNameEditText).text.toString()

        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .build()

        val request = Request.Builder()
            .url("http://cinema.areas.su/auth/register")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@SignUp, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    runOnUiThread {
                        Toast.makeText(this@SignUp, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
                SignIn(email, password);
            }
        })
    }

    private fun SignIn(email: String, password: String) {
        val client = OkHttpClient() //объект клиента

        //Строим FormBody, передаем параметры для отправки
        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        //задание ссылки для передачи параметров
        val request = Request.Builder()
            .url("http://cinema.areas.su/auth/login")
            .post(formBody)
            .build()

        Log.i("INFO", "Request was initialiazed")

        // Вызываем и устанавливаем функцию обратного вызова асинхронно
        client.newCall(request).enqueue(object : Callback {
            //действия при ошибке
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Test", "Test")
                Toast.makeText(applicationContext, "Данные введены неверно", Toast.LENGTH_LONG)
                    .show()
                e.printStackTrace() //--
            }
            //действия при успешном запросе
            @Throws(IOException::class) //--
            override fun onResponse(call: Call, response: Response) {
                try {
                    //если есть ошибка в отправке запроса
                    if (!response.isSuccessful) {
                        //Запускает указанное действие в потоке пользовательского интерфейса. Если текущий поток
                        // является потоком пользовательского интерфейса, то действие выполняется немедленно.
                        // Если текущий поток не является потоком пользовательского интерфейса, действие отправляется
                        // в очередь событий потока пользовательского интерфейса.
                        runOnUiThread {
                            Toast.makeText(
                                this@SignUp,
                                "Неверный логин/пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        throw IOException("Неверный логин/пароль")
                    }
                    //Выдает ошибку AssertionError, если значение равно false
                    assert(response.body() != null)

                    //class представляет неизменяемое значение объекта JSON (неупорядоченный набор из нуля или
                    // более пар имя/значение). Он также обеспечивает неизменяемый вид карты для сопоставлений имени/значения
                    // объекта JSON.
                    val json = JSONObject(response.body()!!.string())


                    GlobalVars.token = json.getInt("token")
                    Log.d("Token", java.lang.String.valueOf(GlobalVars.token))
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}*/


