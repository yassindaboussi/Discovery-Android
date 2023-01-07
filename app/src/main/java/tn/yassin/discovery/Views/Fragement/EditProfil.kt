package tn.yassin.discovery.Views.Fragement

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.yassin.discovery.Utils.BIOUSER
import tn.yassin.discovery.Utils.EMAILUSER
import tn.yassin.discovery.Utils.NAMEUSER
import tn.yassin.discovery.Utils.PREF_NAME
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.R
import tn.yassin.discovery.Views.CustomDialog.*

class EditProfil : Fragment() {
    //lateinit var BottomNavigationView: BottomNavigationView
    lateinit var NamehasSelcted: TextView
    lateinit var EmailhasSelcted: TextView
    lateinit var BiohasSelcted: TextView
    lateinit var PasswordhasSelcted: TextView
    lateinit var BackEdit: ImageView
    private lateinit var MySharedPref: SharedPreferences
    lateinit var  RelativeLayoutEditProfil: RelativeLayout
    ////////////////////////////////////////////////////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiledit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as Navigation).BottomNavigationView.visibility =
            View.GONE // Hide Bottom Nav (Par prof)
        // hideSystemUIAndNavigation()
        initView()
        SetUserData()
        OpenDialogChange() // Open Poup Change For (Name,Email,Bio)
        BackToProfile()
        //FixBackClick()
    }

    fun SetUserData() {
        val NameUser = MySharedPref.getString(NAMEUSER, null)
        val EmailUser = MySharedPref.getString(EMAILUSER, null)
        val BioUser = MySharedPref.getString(BIOUSER, null)
        NamehasSelcted.text = NameUser
        EmailhasSelcted.text = EmailUser
        BiohasSelcted.text = BioUser
    }

    fun OpenDialogChange() {
        NamehasSelcted.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changename, null)
            val msg = DialogChangeName()
            msg.ShowEditName(context, view)
            val txtNameChange = view.findViewById<TextView>(R.id.txtNameChange) as? TextView
            val NameUser = MySharedPref.getString(NAMEUSER, null)
            txtNameChange!!.text = NameUser
        }
        EmailhasSelcted.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changeemail, null)
            val msg = DialogChangeEmail()
            msg.ShowEditEmail(context, view)
            val txtEmailChange = view.findViewById<TextView>(R.id.txtEmailChange) as? TextView
            val EmailUser = MySharedPref.getString(EMAILUSER, null)
            txtEmailChange!!.text = EmailUser
        }
        BiohasSelcted.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changebio, null)
            val msg = DialogChangeBio()
            msg.ShowEditBio(context, view)
            val txtBioChange = view.findViewById<TextView>(R.id.txtBioChange) as? TextView
            val BioUser = MySharedPref.getString(BIOUSER, null)
            txtBioChange!!.text = BioUser
        }
        PasswordhasSelcted.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changepassword, null)
            val msg = DialogChangePassword()
            msg.ShowEditPassword(context, view)
        }
    }

    fun initView() {
        MySharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        NamehasSelcted = requireView().findViewById(R.id.NamehasSelcted)
        EmailhasSelcted = requireView().findViewById(R.id.EmailhasSelcted)
        BiohasSelcted = requireView().findViewById(R.id.BiohasSelcted)
        PasswordhasSelcted = requireView().findViewById(R.id.PasswordhasSelcted)
        BackEdit = requireView().findViewById(R.id.BackEdit)
        RelativeLayoutEditProfil = requireView().findViewById(R.id.RelativeLayoutEditProfil)
    }

    fun BackToProfile() {
        BackEdit.setOnClickListener {
            //fragmentManager?.beginTransaction()?.replace(R.id.container, EditProfil())?.commit()
            fragmentManager?.beginTransaction()?.replace(R.id.container, ProfileFragment())
                ?.addToBackStack("profile")?.commit()
            (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
        }
    }



/*    fun FixBackClick()
    {
        RelativeLayoutEditProfil.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() === 0) {
                println("Back Clicked !!!!!!!!!!!!!!")
                v.clearFocus()
                return@OnKeyListener true
            }
            false
        })
    }*/

    private fun hideSystemUIAndNavigation() {
        requireActivity().window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onDetach() {
        super.onDetach();
        activity?.let{
           // val intent = Intent (it, Navigation::class.java)
            //it.startActivity(intent)
            fragmentManager?.beginTransaction()?.replace(R.id.container, ProfileFragment())
                ?.addToBackStack("profile")?.commit()
            (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
        }
    }

}