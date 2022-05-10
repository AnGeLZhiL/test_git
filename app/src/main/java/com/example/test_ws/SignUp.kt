package com.example.test_ws

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
}