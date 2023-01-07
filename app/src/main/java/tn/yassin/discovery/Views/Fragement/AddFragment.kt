package tn.yassin.discovery.Views.Fragement

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.internal.ContextUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.*
import tn.yassin.discovery.ViewModel.PostsViewModel
import tn.yassin.discovery.ViewModel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddFragment : Fragment() {
    lateinit var btnCloseAdd: ImageView
    lateinit var btnAddPost: Button
    lateinit var txtDescriptionPost: EditText
    lateinit var txtcount:TextView
    lateinit var postImage : TextView
    lateinit var imgPreviewPost :ImageView
    //
    val ReadyFunction = ReadyFunction()
    //
    private lateinit var MySharedPref: SharedPreferences
    private var imgUri: Uri? = null
    var viewModell = PostsViewModel()
    private val STORAGE_PERMISSION_CODE = 111
    private val IMAGE_GALLERY_REQUEST_CODE: Int = 2001
    //
    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_useradd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        ClosePage()
        gettextwathcer()
        SelectImage()
        AddPost(txtDescriptionPost)
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    fun AddPost(txtDescriptionPost:TextView)
    {
        btnAddPost.setOnClickListener {
        val badWords: ArrayList<String> = ArrayList()
        badWords.add("haram")
        badWords.add("bad")

            for (i in 0 until badWords.size) {
            val badWord = badWords[i]
            if (txtDescriptionPost.text.toString().lowercase(Locale.getDefault()).contains(badWord)) {
                val newReplacedString: String = txtDescriptionPost.text.toString().replace(badWord, "***")
                txtDescriptionPost.text = newReplacedString
                println("Yes"+badWord)
                CustomToast(requireContext(), "Bad Words Are Not Allowed Here!","RED").show()
            }
        }
            if(txtDescriptionPost.text.toString().length>0 && imgUri!=null)
            {
             println("Lets Gooo !!!")
             UploadPostUser(requireContext())
            }
            else
            {
                println("Someting Emptyyyyyyyy !!!")

            }
            if(imgUri==null)
            {
                CustomToast(requireContext(), "No Picture Has Selected!","RED").show()
            }
        }
    }

    fun initViews() {
        btnCloseAdd = requireView().findViewById(R.id.btnCloseAdd)
        btnAddPost = requireView().findViewById(R.id.btnAddPost)
        txtDescriptionPost = requireView().findViewById(R.id.txtDescriptionPost)
        txtcount = requireView().findViewById(R.id.txtcountPostAdd)
        postImage = requireView().findViewById(R.id.postImage)
        imgPreviewPost = requireView().findViewById(R.id.imgPreviewPost)
    }

    fun ClosePage() {
        btnCloseAdd.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Navigation::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }

    private fun gettextwathcer() {
        txtDescriptionPost.addTextChangedListener(txtWatcher)
    }

    private val txtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
/*            println("onchanged "+txtDescriptionPost.text)
            println("onchanged "+txtDescriptionPost.text.length)*/
            if(txtDescriptionPost.text.length==0)
            {
                btnAddPost.setEnabled(false);
                btnAddPost.setBackgroundResource(R.drawable.disabledbutton);
                btnAddPost.setTextColor(Color.parseColor("#CECFD1"));
            }
            else
            {
                btnAddPost.setBackgroundResource(R.drawable.allowbutton);
                btnAddPost.setTextColor(ContextCompat.getColor(context!!, R.color.white));
            }
            txtcount.setText(txtDescriptionPost.text.length.toString()+"/250")
        }
        override fun afterTextChanged(s: Editable) {
/*            println("afterchanged "+txtDescriptionPost.text)
            println("afterchanged "+txtDescriptionPost.text.length)*/
        }
    }


    @SuppressLint("Recycle")
    fun UploadPostUser(context: Context){
        //
        MySharedPref =
            context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val IdUser = MySharedPref.getString(IDUSER, null)
        val TokenUser = MySharedPref.getString(TOKENUSER, null)
        val NameUser = MySharedPref.getString(NAMEUSER, null)
        val AvatarUser = MySharedPref.getString(AVATARUSER, null)
        //
        val fileDir=context.filesDir
        val file= File(fileDir,"image.jpg")
        val inputStream=context.contentResolver.openInputStream(imgUri!!)
        val outputStream= FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", file.name,requestBody)

        viewModell= ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)

        val Description = txtDescriptionPost.text .toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val idUser=IdUser.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val nameuser= NameUser.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val avataruser=AvatarUser.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        viewModell.UploadPostUser(Description,idUser,image,context,nameuser,avataruser)
        //
        viewModell._UserLiveData.observe(viewLifecycleOwner,{
            if (it!=null){
                CustomToast(requireContext(), "Post Added Successfully!","GREEN").show()
                BackToProfile()
                ReadyFunction.hideKeyboard(txtDescriptionPost, context)
            }else{
                CustomToast(requireContext(), "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }

    fun BackToProfile() {
            //fragmentManager?.beginTransaction()?.replace(R.id.container, EditProfil())?.commit()
            fragmentManager?.beginTransaction()?.replace(R.id.container, ProfileFragment())
                ?.addToBackStack("profile")?.commit()
            (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE

    }

    //////////////////////////////////////////////////////////////

    fun SelectImage()
    {
        postImage.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        //requestStoragePermission()
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).apply {
            type="image/*"
            startActivityForResult(this,IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_GALLERY_REQUEST_CODE) {
            if (data != null && data.data != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    imgUri= data.data!!
                    imgPreviewPost.setImageURI(imgUri)
                    ////
                }
            }
        }
    }

    private fun requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            AlertDialog.Builder(requireContext())
                .setTitle("Autorisation nécessaire")
                .setMessage("Cette autorisation est nécessaire pour ajouter une image")
                .setPositiveButton("D'accord",
                    DialogInterface.OnClickListener { dialog, which ->
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
                    })
                .setNegativeButton("Annuler",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .create().show()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
                Toast.makeText(requireContext(), "Permission accordée", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }
    }

}