package com.example.android.rest_testing.net.retrofit

import android.util.Log
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.RestInterface
import retrofit.ResponseCallback
import retrofit.RestAdapter

class RestRetrofit: RestInterface {
    private val userService: UserService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP + ":8080/"
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    override fun loginUser(user: UserShort): Boolean {
        try {
            var loggedUser = userService.loginUser(user)
            println(loggedUser)
            return true
        }
        catch (ex: Exception) {
            Log.d("custom", ""+ex.toString());
        }
        return false
    }

    override fun registerUser(user: User): Boolean {
        try{
            var registeredUser = userService.registerUser(user)
            println(registeredUser)
            return true
        }
        catch (ex: Exception) {
            Log.d("custom", ""+ex.toString());
        }
        return false
    }

    /*
    override fun getListOfCourses(): List<ShortCourse>? {
        return service.listOfCourses
    }

    override fun getCourse(id: Long?): Course? {
        return service.getCourse(id)
    }

    override fun getCourseStudents(courseId: Long?): List<ShortPerson>? {
        return service.getCourseStudents(courseId)
    }

    override fun getListOfPersons(): List<ShortPerson>? {
        return service.listOfPersons
    }

    override fun getPerson(id: Long?): Person? {
        return service.getPerson(id)
    }

    override fun createPerson(person: Person?) {
        service.createPerson(person)
    }

    override fun deletePerson(id: Long?) {
        for(course in service.listOfCourses){
            disenrollPersonFromCourse(id, course.id)
        }
        service.deletePerson(id)
    }

    override fun enrollPersonToCourse(personId: Long?, courseId: Long?): Boolean {
        if(getCourseStudents(courseId) == null || getPerson(personId) == null) return false
        if((getCourseStudents(courseId)!!.stream().mapToLong{ t -> t.id!!}.toArray().contains(personId!!)).not()){
            service.enrollPersonToCourse(personId, courseId, Object())
            return true
        }
        return false
    }

    override fun disenrollPersonFromCourse(personId: Long?, courseId: Long?): Boolean {
        if(getCourseStudents(courseId) == null || getPerson(personId) == null) return false
        if(getCourseStudents(courseId)!!.stream().mapToLong{ t -> t.id!!}.toArray().contains(personId!!)){
            service.disenrollPersonFromCourse(personId, courseId, Object())
            return true
        }
        return false
    }
    */
}