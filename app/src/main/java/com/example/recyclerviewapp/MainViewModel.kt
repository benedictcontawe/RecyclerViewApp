package com.example.recyclerviewapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor(application : Application) : super(application) {
        setItems()
    }

    private val liveList : MutableLiveData<List<CustomModel>> = MutableLiveData<List<CustomModel>>()

    public fun setItems() {
        val list = mutableListOf<CustomModel>()
        list.clear()
        list.add( IconModel(icon = R.drawable.ic_blender, name = "A", detail = getApplication<Application>().resources.getString(R.string.a_details)) )
        list.add( NameModel(name = "B", detail = getApplication<Application>().resources.getString(R.string.b_details)) )
        list.add( NameModel(name = "C", detail = getApplication<Application>().resources.getString(R.string.c_details)) )
        list.add( NameModel(name = "D", detail = getApplication<Application>().resources.getString(R.string.d_details)) )
        list.add( IconModel(icon = R.drawable.ic_umbrella, name = "E", detail = getApplication<Application>().resources.getString(R.string.e_details)) )
        list.add( NameModel(name = "F", detail = getApplication<Application>().resources.getString(R.string.f_details)) )
        list.add( NameModel(name = "G", detail = getApplication<Application>().resources.getString(R.string.g_details)) )
        list.add( NameModel(name = "H", detail = getApplication<Application>().resources.getString(R.string.h_details)) )
        list.add( IconModel(icon = R.drawable.ic_wind_power, name = "I", detail = getApplication<Application>().resources.getString(R.string.i_details)) )
        list.add( NameModel(name = "J", detail = getApplication<Application>().resources.getString(R.string.j_details)) )
        list.add( NameModel(name = "K", detail = getApplication<Application>().resources.getString(R.string.k_details)) )
        list.add( NameModel(name = "L", detail = getApplication<Application>().resources.getString(R.string.l_details)) )
        list.add( IconModel(icon = R.drawable.ic_blender, name = "M", detail = getApplication<Application>().resources.getString(R.string.m_details)) )
        list.add( NameModel(name = "N", detail = getApplication<Application>().resources.getString(R.string.n_details)) )
        list.add( NameModel(name = "O", detail = getApplication<Application>().resources.getString(R.string.o_details)) )
        list.add( IconModel(icon = R.drawable.ic_wind_power, name = "P", detail = getApplication<Application>().resources.getString(R.string.p_details)) )
        list.add( IconModel(icon = R.drawable.ic_umbrella, name = "Q", detail = getApplication<Application>().resources.getString(R.string.q_details)) )
        list.add( IconModel(icon = R.drawable.ic_blender, name = "R", detail = getApplication<Application>().resources.getString(R.string.r_details)) )
        list.add( NameModel(name = "S", detail = getApplication<Application>().resources.getString(R.string.s_details)) )
        list.add( NameModel(name = "T", detail = getApplication<Application>().resources.getString(R.string.t_details)) )
        list.add( IconModel(icon = R.drawable.ic_wind_power, name = "U", detail = getApplication<Application>().resources.getString(R.string.u_details)) )
        list.add( NameModel(name = "V", detail = getApplication<Application>().resources.getString(R.string.v_details)) )
        list.add( NameModel(name = "W", detail = getApplication<Application>().resources.getString(R.string.w_details)) )
        list.add( NameModel(name = "X", detail = getApplication<Application>().resources.getString(R.string.x_details)) )
        list.add( NameModel(name = "Y", detail = getApplication<Application>().resources.getString(R.string.y_details)) )
        list.add( IconModel(icon = R.drawable.ic_question_mark, name = "Z", detail = getApplication<Application>().resources.getString(R.string.z_details)) )
        liveList.setValue(list)
    }

    public fun getLiveList() : LiveData<List<CustomModel>> {
        return liveList
    }

    override fun onCleared() {
        super.onCleared()
    }

}