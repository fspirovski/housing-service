package mk.ukim.finki.mpip.housing_service.service

import android.content.Intent
import mk.ukim.finki.mpip.housing_service.AuthActivity
import mk.ukim.finki.mpip.housing_service.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val localStorageService = LocalStorageService()

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = localStorageService.getData("jwt", null)
        val response = if (token != null) {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            chain.proceed(newRequest)
        } else {
            chain.proceed(chain.request())
        }

        if (response.code() == 401) {
            logOut()
        }

        return response
    }

    private fun logOut() {
        localStorageService.saveData("jwt", null);
        redirectToAuthActivity()
    }

    private fun redirectToAuthActivity() {
        val context = MyApplication.applicationContext()
        val intent = Intent(context, AuthActivity::class.java)

        context.startActivity(intent)
    }
}