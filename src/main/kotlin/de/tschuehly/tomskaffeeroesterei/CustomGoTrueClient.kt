package de.tschuehly.tomskaffeeroesterei

import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.GoTrueDefaultClient
import io.supabase.gotrue.http.GoTrueHttpClientApache
import io.supabase.gotrue.http.GoTrueHttpException
import io.supabase.gotrue.json.GoTrueJsonConverterJackson
import org.apache.hc.client5.http.impl.classic.HttpClients

val goTrueJsonConverter = GoTrueJsonConverterJackson()
class CustomGoTrueClient: GoTrueClient(
    goTrueHttpClient = GoTrueHttpClientApache(
                url = "https://qvgwcufbvjuvyaqzpwhu.supabase.co/auth/v1",
                headers = mapOf("apiKey" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2Z3djdWZidmp1dnlhcXpwd2h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2NDQzMjgzODIsImV4cCI6MTk1OTkwNDM4Mn0.-Uzx4NWiyTkNPjlUJwEUFkrn9YKYoTiRWj7ZIwCXEjU"),
                httpClient = { HttpClients.createDefault() },
                goTrueJsonConverter = goTrueJsonConverter
        ),
        goTrueJsonConverter = goTrueJsonConverter){
    fun signupWithEmail(){

    }
}
