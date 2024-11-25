package com.example.randomapp.DatabaseImpl

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayThrough(
    @PrimaryKey(autoGenerate = true) var entryNo: Int,
    @ColumnInfo(name= "name") val name: String,
    @ColumnInfo(name= "numPairs") val pairsAttempted: Int,
    @ColumnInfo(name= "Guesses") var guessesUsed: Int,
    @ColumnInfo(name= "WeightedScore") var weightedScore: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(entryNo)
        parcel.writeString(name)
        parcel.writeInt(pairsAttempted)
        parcel.writeInt(guessesUsed)
        parcel.writeDouble(weightedScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayThrough> {
        override fun createFromParcel(parcel: Parcel): PlayThrough {
            return PlayThrough(parcel)
        }

        override fun newArray(size: Int): Array<PlayThrough?> {
            return arrayOfNulls(size)
        }
    }

}
