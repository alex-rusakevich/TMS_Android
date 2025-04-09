import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DynamicFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment): Int {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)

        return fragments.size - 1
    }

    fun removeFragment(index: Int) {
        fragments.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}