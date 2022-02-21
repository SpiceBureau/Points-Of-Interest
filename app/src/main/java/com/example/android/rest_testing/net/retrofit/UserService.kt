package com.example.android.rest_testing.net.retrofit

import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserShort
import retrofit.Callback
import retrofit.ResponseCallback
import retrofit.http.Body
import retrofit.http.POST

interface UserService {
    @POST("/loginUser")
    fun loginUser(@Body user: UserShort): JWT

    @POST("/registerUser")
    fun registerUser(@Body user: User): String

    /*
        @get:GET("/courses")
    val listOfCourses: List<ShortCourse>

    @GET("/courses/{id}")
    fun getCourse(@Path("id") id: Long?): Course

    @GET("/courses/{id}/students")
    fun getCourseStudents(@Path("id") courseId: Long?): List<ShortPerson>

    @get:GET("/persons")
    val listOfPersons: List<ShortPerson>

    @GET("/persons/{id}")
    fun getPerson(@Path("id") id: Long?): Person

    @POST("/persons")
    fun createPerson(@Body person: Person?) : String

    @DELETE("/persons/{id}")
    fun deletePerson(@Path("id") id: Long?): String

    @POST("/courses/{courseId}/enrollPerson/{personId}")
    fun enrollPersonToCourse(@Path("personId") personId: Long?, @Path("courseId") courseId: Long?, @Body o: Any): String

    @POST("/courses/{courseId}/unenrollPerson/{personId}")
    fun disenrollPersonFromCourse(@Path("personId") personId: Long?, @Path("courseId") courseId: Long?, @Body o: Any): String
     */
}