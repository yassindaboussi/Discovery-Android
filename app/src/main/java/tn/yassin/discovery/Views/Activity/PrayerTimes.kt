package tn.yassin.discovery.Views.Activity


import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.CustomDialogs
import tn.yassin.discovery.Utils.ReadyFunc
import tn.yassin.prayertimes.PrayJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//
var FajrResult = "--:--"
var DhuhrResult = "--:--"
var AsarResult = "--:--"
var MaghrebResult = "--:--"
var IshaResult = "--:--"
//

class PrayerTimes : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null

    private lateinit var txtFajr: TextView
    private lateinit var txtDhuhr: TextView
    private lateinit var txtAsar: TextView
    private lateinit var txtMaghrib: TextView
    private lateinit var txtIsha: TextView
    private lateinit var txtNextSalat: TextView
    private lateinit var txtTimeNextSalat: TextView
    ////////////////////////////////////////////////
    private lateinit var imgSound1: ImageView
    private lateinit var imgSound2: ImageView
    private lateinit var imgSound3: ImageView
    private lateinit var imgSound4: ImageView
    private lateinit var imgSound5: ImageView
    ///
    val Ready = ReadyFunc()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prayertimes)

        InitView()
        DayName()
        DynamicClock()
        SearchPosition()
        imgSoundClicked(imgSound1)
        imgSoundClicked(imgSound2)
        imgSoundClicked(imgSound3)
        imgSoundClicked(imgSound4)
        imgSoundClicked(imgSound5)

        ReadPositionData()

        FirstTimeRun()
    }

    fun FirstTimeRun() {
        val sharedPref = getSharedPreferences("PrayerTimes", Context.MODE_PRIVATE)
        val FirstTimeRun = sharedPref.getBoolean("FirstTimeRun", true)
        if (FirstTimeRun) {
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.informations, null)
            val msg = CustomDialogs()
            msg.ShowDialogInformations(this, view)
        }
    }


    fun InitView() {
        txtFajr = findViewById(R.id.txtFajr)
        txtDhuhr = findViewById(R.id.txtDhuhr)
        txtAsar = findViewById(R.id.txtAsar)
        txtMaghrib = findViewById(R.id.txtMaghrib)
        txtIsha = findViewById(R.id.txtIsha)
        txtNextSalat = findViewById(R.id.txtNextSalat)
        txtTimeNextSalat = findViewById(R.id.txtTimeNextSalat)
        //RestTime = findViewById(R.id.RestTime)
        //////////////////////////////////////////
        imgSound1 = findViewById(R.id.imgSound1)
        imgSound2 = findViewById(R.id.imgSound2)
        imgSound3 = findViewById(R.id.imgSound3)
        imgSound4 = findViewById(R.id.imgSound4)
        imgSound5 = findViewById(R.id.imgSound5)
    }


    fun SearchPosition() {

        val txtEsmDewla = findViewById<EditText>(R.id.txtEsmDewla)
        val txtEsmWleya =  findViewById<EditText>(R.id.txtEsmWleya)
        val btnSearch = findViewById<ImageView>(R.id.btnSearch)
        btnSearch.setOnClickListener {
            val Dewla = txtEsmDewla.text.toString()
            val Wleya = txtEsmWleya.text.toString()
            HideKeyboard(txtEsmDewla)
            //LoadPrayTimeAsync(Wleya,Dewla)
            SavePositionData(Dewla,Wleya)
            if(Dewla.isEmpty() || Wleya.isEmpty()) {
                Toast.makeText(applicationContext, "يجب ملئ المكان للبحث", Toast.LENGTH_LONG).show()
            }
            else{
                if (Ready.isOnline(this)) {
                    LoadPrayTimeAsync(Wleya,Dewla)
                } else {
                    val factory = LayoutInflater.from(this)
                    val view: View = factory.inflate(R.layout.noconnection, null)
                    val msg = CustomDialogs()
                    msg.ShowDialogNoConnection(this, view)
                }
            }
        }
    }

    fun HideKeyboard(textView: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(textView.windowToken, 0)
    }


    fun DynamicClock() {
        val textClock = findViewById<View>(R.id.textClock) as TextClock
        textClock.format12Hour = null
        //textClock.format12Hour = "hh:mm:ss "
        //textClock.format24Hour = "k:mm:ss"
    }

    fun DayName() {
        val txtDayNow = findViewById<TextView>(R.id.txtDayNow)
        txtDayNow.text = getdayName()
    }

    fun getdayName(): String {
        var day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);
        when (day) {
            1 -> return "الأحد"
            2 -> return "الاثنين"
            3 -> return "الثلاثاء"
            4 -> return "الأربعاء"
            5 -> return "الخميس"
            6 -> return "الجمعة"
            7 -> return "السبت"
            else -> return "الأحد"
        }
    }

    fun LoadPrayTimeAsync(Wleya :  String , Dewla: String  ) {

        val client = AsyncHttpClient()
        val url =
            "http://api.aladhan.com/v1/timingsByCity?city=" + Wleya + "&country=" + Dewla + "&method=8"
        client.get(url, object : JsonHttpResponseHandler() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                super.onSuccess(statusCode, headers, response)
                val gson = Gson()
                val pray = gson.fromJson(response.toString(), PrayJson::class.java)

                FajrResult = pray.data.timings.Fajr
                DhuhrResult = pray.data.timings.Dhuhr
                AsarResult = pray.data.timings.Asr
                MaghrebResult = pray.data.timings.Maghrib
                IshaResult = pray.data.timings.Isha

                //////////////////////////////////////////////::
                txtFajr.text = FajrResult
                txtDhuhr.text = DhuhrResult
                txtAsar.text = AsarResult
                txtMaghrib.text = MaghrebResult
                txtIsha.text = IshaResult
                /////////////////////////////////////////////::
                ThenextSalatis(FajrResult, DhuhrResult, AsarResult, MaghrebResult, IshaResult)
                /////////////////////////////////////////////::
/*                itsTimeToPrayOrNot(txtFajr.text.toString())
                itsTimeToPrayOrNot(txtDhuhr.text.toString())
                itsTimeToPrayOrNot(txtAsar.text.toString())
                itsTimeToPrayOrNot(txtMaghrib.text.toString())
                itsTimeToPrayOrNot(txtIsha.text.toString())*/
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
                super.onFailure(statusCode, headers, throwable, errorResponse)
                println(throwable?.message)
            }
        })

    }


    fun SavePositionData(txtEsmDewla: String, txtEsmWleya: String) {
        val sharedPreferences = getSharedPreferences("PrayTime", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Dawla", txtEsmDewla)
        editor.putString("Wileya", txtEsmWleya)
        editor.apply()
    }

    fun ReadPositionData() {

        val txtEsmDewla = findViewById<EditText>(R.id.txtEsmDewla)
        val txtEsmWleya =  findViewById<EditText>(R.id.txtEsmWleya)
        val sharedPreferences = getSharedPreferences("PrayTime", Context.MODE_PRIVATE)
        val Dawla = sharedPreferences.getString("Dawla", "")
        val Wileya = sharedPreferences.getString("Wileya", "")
        if(Dawla!!.isNotEmpty() && Wileya!!.isNotEmpty()) {
            txtEsmDewla.setText(Dawla)
            txtEsmWleya.setText(Wileya)
            LoadPrayTimeAsync(Wileya,Dawla)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ThenextSalatis(Fajer: String, Dhoher: String, Asar: String, Moghreb: String, Isha: String) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = current.format(formatter)

        var NexTsalaT = ""

        if (currentDate <= Fajer || currentDate > Isha) {
            txtNextSalat.setText("الفجر")
            txtTimeNextSalat.text = Fajer
            NexTsalaT = Fajer
        }
        if (currentDate > Fajer && currentDate <= Dhoher) {
            txtNextSalat.setText("الضهر")
            txtTimeNextSalat.text = Dhoher
            NexTsalaT = Dhoher
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate <= Asar) {
            txtNextSalat.setText("العصر")
            txtTimeNextSalat.text = Asar
            NexTsalaT = Asar
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate > Asar && currentDate <= Moghreb) {
            txtNextSalat.setText("المغرب")
            txtTimeNextSalat.text = Moghreb
            NexTsalaT = Moghreb
        }
        if (currentDate > Fajer && currentDate > Dhoher && currentDate > Asar && currentDate > Moghreb && currentDate <= Isha) {
            txtNextSalat.setText("العشاء")
            txtTimeNextSalat.text = Isha
            NexTsalaT = Isha
        }

        //CalculateDiffTwoTimes(NexTsalaT)
    }

    /* @SuppressLint("SimpleDateFormat")
     @RequiresApi(Build.VERSION_CODES.O)
     fun CalculateDiffTwoTimes(NexTsalaT: String) {

         val Boucle = object : Thread() {
             override fun run() {
                 while (!isInterrupted) {
                     try {
                         runOnUiThread {
                             ////////////////////////////////////////////////////////////
                             val format = SimpleDateFormat("HH:mm:ss")
                             val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                             val TimeNow = LocalDateTime.now().format(formatter)
                             var TimeTaw: Date?
                             TimeTaw = format.parse(TimeNow)

                             val FajrWithSec: String = NexTsalaT + ":" + "00"
                             var FajarSec: Date?
                             FajarSec = format.parse(FajrWithSec)

                             val diff: Long = FajarSec.getTime() - TimeTaw.getTime()
                             val diffSeconds = diff / 1000
                             val hours = diffSeconds / 3600
                             val minutes = diffSeconds % 3600 / 60
                             val seconds = diffSeconds % 60
                             val timeString = String.format(
                                 "%02d:%02d:%02d",
                                 hours,
                                 minutes,
                                 seconds
                             )
                             RestTime.text = timeString
                         }
                     } catch (e: InterruptedException) {
                         e.printStackTrace()
                     }
                 }
             }
         }
         Boucle.start()
     }*/


    fun imgSoundClicked(imgSound : ImageView)
    {
        var showingFirst = true
        imgSound.setOnClickListener {
            if(showingFirst == true){
                imgSound.setImageResource(R.drawable.soundoff)
                showingFirst = false;
            }else{
                imgSound.setImageResource(R.drawable.sound)
                showingFirst = true;
            }
        }
    }

/*    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun itsTimeToPrayOrNot(salaT: String)
    {
          val boucle = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(1000)
                        runOnUiThread {

                            val sdf = SimpleDateFormat("hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            ////
                            val salaTWithSec: String = salaT + ":" + "00"

                            if(currentDate.toString() == salaTWithSec)
                            {
                                PlayAdhan()
                                println("Alllllaaaaho Akkbbbbbbbbeeeeerrrr")
                            }
                            println("TimeTaw "+currentDate.toString())
                            println("salaTSec "+salaTWithSec)

                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        boucle.start()

    }*/


    fun PlayAdhan() {
        if (mMediaPlayer == null) {
            //mMediaPlayer = MediaPlayer.create(this, R.raw.adhan)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun RuninBackground()
    {
        val thread = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(1000)
                        runOnUiThread {

                            /// Code Here


                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        thread.start()
    }
}