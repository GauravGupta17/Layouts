package com.example.utilis

import android.content.Context
import android.content.Intent
import com.example.layouts.*

class Utils {

    companion object Utility {
        fun handleClick(context: Context, position: Int) {

            var intent = Intent()

            when (position) {

                0 -> intent = Intent(context, MotionActivity::class.java)

                1 -> intent = Intent(context, ImageFilterActivity::class.java)

                2 -> intent = Intent(context, ContactListActivity::class.java)

                3 -> intent = Intent(context, AnimationsActivity::class.java)

                4 -> intent = Intent(context, FragmentsActivity::class.java)

                5 -> intent = Intent(context, SharedPrefActivity::class.java)

                6 -> intent = Intent(context, FilesActivity::class.java)

                7 -> intent = Intent(context, TestViewModel::class.java)

                8 -> intent = Intent(context, FormActivity::class.java)

                9 -> intent = Intent(context, UserInfoActivity::class.java)

                10 -> intent = Intent(context, PlayListActivity::class.java)

                11 -> intent = Intent(context, ContentProviderActivity::class.java)

                12 -> intent = Intent(context, SchoolDetailActivity::class.java)

                13 -> intent = Intent(context, LogInPlayListActivity::class.java)

                14 -> intent = Intent(context, EmailActivity::class.java)

                15 -> intent = Intent(context, StorageActivity::class.java)

                16 -> intent = Intent(context, FireBaseMessge::class.java)

                17 -> intent = Intent(context, FireStoreActivity::class.java)

                18 -> intent = Intent(context, RealTimeDataBaseActivity::class.java)

                19 -> intent = Intent(context, ViewPagerActivity::class.java)

                20 -> intent = Intent(context, TabViewPagerActivity::class.java)

                21 -> intent = Intent(context, ViewPager2Activity::class.java)

                22 -> intent = Intent(context, ServiceActivity::class.java)

                23 -> intent = Intent(context, WorkManagerActivity::class.java)

                24 -> intent = Intent(context,RetrofitActivity::class.java)

                25 -> intent = Intent(context,NavigationActivity::class.java)

                26 ->intent = Intent(context,ConditionalNavigationActivity::class.java)

            }

            context.startActivity(intent)

        }


    }


}