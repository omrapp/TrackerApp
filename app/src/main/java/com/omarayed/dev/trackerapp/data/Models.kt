package com.omarayed.dev.trackerapp.data

import android.os.Parcel
import android.os.Parcelable


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeInt(userId)
        p0.writeString(title)
        p0.writeString(body)
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }


}