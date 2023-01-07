package tn.yassin.discovery.Views.Fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import tn.yassin.discovery.ExploreFragmenttodo
import tn.yassin.discovery.Navigation
import tn.yassin.discovery.R


class FragmentVR : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //
                fragmentManager?.beginTransaction()?.replace(R.id.container, ExploreFragmenttodo())
                    ?.addToBackStack("ExploreTodo")?.commit()
                (requireActivity() as Navigation).BottomNavigationView.visibility = View.VISIBLE
            }
        })
    }


}