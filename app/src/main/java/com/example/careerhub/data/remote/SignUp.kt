package com.example.careerhub.data.remote

import com.example.careerhub.model.AuthenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUp {
    @FormUrlEncoded
    @POST("/postRegister")
    suspend fun signUp(
        @Field("email") username: String,
        @Field("password") password: String
    ): Response<AuthenResponse>
}