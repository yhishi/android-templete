package com.temlete.presentation.common

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.temlete.presentation.dashboard.DashboardFragment
import com.temlete.presentation.home.HomeFragment
import com.temlete.presentation.notifications.NotificationsFragment

/** Navigationでfragmentを切り替える際のカスタムナビゲーター
 *  切り替え時にfragmentを再生成しない
 *  @Navigator.Nameに指定したタグ名でNavigation.xmlにfragmentを記述する
 **/
@Navigator.Name("fragment")
class CustomNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    @Suppress("DEPRECATION")
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (manager.isStateSaved) {
            return null
        }

        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }

        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        // BottomNavigationのfragmentでないことを表すフラグ
        var isNotBottomNavigation = true
        BottomNavigationFragment.values().forEach { bottomNavigationList ->
            if (destination.className == bottomNavigationList.className) {
                isNotBottomNavigation = false
            }
        }

        var fragment = manager.findFragmentByTag(tag)

        // fragment初期作成時 or BottomNavigationのfragmentでない場合
        if (fragment == null || isNotBottomNavigation) {
            fragment = instantiateFragment(context, manager, className, args)

            // fragmentを生成
            transaction.add(containerId, fragment, tag)
        }

        fragment.arguments = args

        transaction.show(fragment)
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.commit()

        return destination
    }
}

class CustomNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return CustomNavigator(requireContext(), childFragmentManager, id)
    }
}

/** BottomNavigationのFragment一覧
 *  この一覧に追加したFragmentは、再表示した時にFragmentが再生成されない
 *  BottomNavigation(下タブメニュー)を切り替えた時に、再ロードしないための実装
 **/
enum class BottomNavigationFragment(val className: String) {
    HOME(HomeFragment::class.java.name),
    DASHBOARD(DashboardFragment::class.java.name),
    NOTIFICATION(NotificationsFragment::class.java.name)
}