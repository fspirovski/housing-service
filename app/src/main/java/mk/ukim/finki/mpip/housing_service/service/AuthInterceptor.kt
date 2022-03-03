package mk.ukim.finki.mpip.housing_service.service

import android.content.Intent
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import mk.ukim.finki.mpip.housing_service.AuthActivity
import mk.ukim.finki.mpip.housing_service.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val localStorageService = LocalStorageService()

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = localStorageService.getData("jwt", null)
        val isInvalid = if (token == null) true else {
            try {
                JWT(token).isExpired(0)
            } catch (e: DecodeException) {
                true
            }
        }
        val response = if (!isInvalid) {
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

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}