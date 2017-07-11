package com.android.marco.firstdecision.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ListView
import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.activities.MainActivity
import com.android.marco.firstdecision.adapters.ListThingsAdapter
import com.android.marco.firstdecision.utils.AnimationUtil

/**
 * A simple [Fragment] subclass.

 */
class OneOfManyFragment : Fragment() {

    private var listIsShowing = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val a = activity as MainActivity
        val adapter = ListThingsAdapter(a,
                R.id.lv_oneofmany_things, a.listData, true)
        val rootView = inflater!!.inflate(R.layout.fragment_one_of_many, container, false)
        val listView = rootView.findViewById(R.id.lv_oneofmany_things) as ListView
        listView.adapter = adapter
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_one_of_many, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_show_list) {
            val resultBtn = activity.findViewById(R.id.btn_ma_result)
            if (resultBtn.visibility == View.VISIBLE && !listIsShowing) {
                resultBtn.visibility = View.INVISIBLE
            } else if (resultBtn.visibility == View.INVISIBLE && listIsShowing) {  // && notplaying
                resultBtn.visibility = View.VISIBLE
            }
            slideList()
            return true
        }

        //        if (item.getItemId() == R.id.action_flip_now) {
        //            interactionListener.onInteraction(InteractionListener.Interaction.IA__GO_FLIP);
        //            return true;
        //        }

        return super.onOptionsItemSelected(item)
    }

    private fun slideList() {
        val listView = activity.findViewById(R.id.container_rel) as ViewGroup
        AnimationUtil.slideInOut(activity,
                listView, listIsShowing)
        val viewState: Int
        if (listIsShowing) {
            listView.isEnabled = false
            viewState = View.GONE
        } else {
            listView.isEnabled = true
            viewState = View.VISIBLE
        }
        //        listView.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                listView.clearAnimation();
        //                listView.setVisibility(viewState);
        //            }
        //        }, 810);
        listIsShowing = !listIsShowing
    }


}
