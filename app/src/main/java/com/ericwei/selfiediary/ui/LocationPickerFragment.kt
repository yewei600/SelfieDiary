package com.ericwei.selfiediary.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ericwei.selfiediary.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient


class LocationPickerFragment : Fragment() {

    // New variables for Current Place Picker
    private val TAG = "LocationPickerFragment"
    private lateinit var mListAdapter: ArrayAdapter<String?>
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var mLastKnownLocation: Location? = null

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    private val DEFAULT_ZOOM = 15F
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted: Boolean = false

    // Used for selecting the current place.
    private val M_MAX_ENTRIES = 20
    private var mLikelyPlaceNames = mutableListOf<String?>()
    private var mLikelyPlaceAddresses = mutableListOf<String?>()
    private var mLikelyPlaceAttributions = mutableListOf<String?>()
    private var mLikelyPlaceLatLngs = mutableListOf<LatLng?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_location_picker, container, false)

        val placesList = view.findViewById<ListView>(R.id.listPlaces)
        mListAdapter = ArrayAdapter(this.context, android.R.layout.simple_list_item_1, mLikelyPlaceNames)
        placesList.adapter = mListAdapter
        placesList.onItemClickListener = listClickedHandler

        Places.initialize(context!!, getString(R.string.google_maps_key))
        mPlacesClient = Places.createClient(this.context!!)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.context!!)

        return view
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true

            getDeviceLocation()
        } else {
            ActivityCompat.requestPermissions(
                parentFragment!!.activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true

                //location granted
                getDeviceLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentPlaceLikelihoods() {
        var placeFields = listOf<Place.Field>(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        var placeResponse = mPlacesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result!!
                val count =
                    if (response.placeLikelihoods.size < M_MAX_ENTRIES) response.placeLikelihoods.size else M_MAX_ENTRIES
                for (i in 0 until count) {
                    val curPlace = response.placeLikelihoods[i].place
                    mLikelyPlaceNames.add(curPlace.name)
                    mLikelyPlaceAddresses.add(curPlace.address)
                    mLikelyPlaceAttributions.add(" " + curPlace.attributions)
                    mLikelyPlaceLatLngs.add(curPlace.latLng)

                    if (i > (count - 1)) {
                        break
                    }
                }
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)

                    //fill fake data
                    mLikelyPlaceNames.add("API quota reached")
                }
            }
            mListAdapter.notifyDataSetChanged()
        }
    }

    private fun getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result
                        Log.d(TAG, "Latitude: " + mLastKnownLocation!!.latitude)
                        Log.d(TAG, "Longitude: " + mLastKnownLocation!!.longitude)
//                        mMap.moveCamera(
//                            CameraUpdateFactory.newLatLngZoom(
//                                LatLng(
//                                    mLastKnownLocation!!.latitude,
//                                    mLastKnownLocation!!.longitude
//                                ), DEFAULT_ZOOM
//                            )
//                        )
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.exception)
//                        mMap.moveCamera(
//                            CameraUpdateFactory
//                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM)
//                        )
                    }
                    getCurrentPlaceLikelihoods()
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    private val listClickedHandler = AdapterView.OnItemClickListener { parent, view, position, id ->
        // position will give us the index of which place was selected in the array
        val markerLatLng = mLikelyPlaceLatLngs[position]
        var markerSnippet = mLikelyPlaceAddresses[position]
        if (mLikelyPlaceAttributions[position] != null) {
            markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
        }
    }
}
