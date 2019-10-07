package xyz.sangcomz.chameleonsample

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import xyz.sangcomz.chameleon.Chameleon
import xyz.sangcomz.chameleon.model.ButtonSettingBundle
import xyz.sangcomz.chameleon.model.TextSettingBundle
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val mChameleonAdapter: ChameleonAdapter by lazy { ChameleonAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root.apply {
            setEmptyButtonClickListener { Toast.makeText(this@MainActivity, "Empty Button!", Toast.LENGTH_LONG).show() }
            setErrorButtonClickListener { Toast.makeText(this@MainActivity, "Error Button!", Toast.LENGTH_LONG).show() }
            setStateChangeListener { newState, oldState ->
                Toast.makeText(this@MainActivity, "state was $oldState and now is $newState", Toast.LENGTH_LONG).show()
            }
        }
        setChameleonList()
    }

    private fun setChameleonList() {
        rv_main_list.apply {
            adapter = mChameleonAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
        getChameleons()
                .delay(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            mChameleonAdapter.setChameleonList(it)
                            root.showState(Chameleon.STATE.CONTENT)
                        },
                        {
                            root.showState(Chameleon.STATE.ERROR)
                        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_displayState -> {
                Toast.makeText(this, "State is ${root.getState()}", Toast.LENGTH_LONG).show()
            }
            R.id.menu_content -> {
                root.showState(Chameleon.STATE.CONTENT)
            }
            R.id.menu_loading -> {
                root.showState(Chameleon.STATE.LOADING)
            }
            R.id.menu_empty -> {
                root.showState(Chameleon.STATE.EMPTY,
                        ContextCompat.getDrawable(this, R.drawable.ic_chameleon_red))
            }
            R.id.menu_error -> {
                root.showState(Chameleon.STATE.ERROR,
                        ContextCompat.getDrawable(this, R.drawable.ic_chameleon_blue),
                        TextSettingBundle("Error Bundle Title"),
                        TextSettingBundle("Error Bundle Content"),
                        ButtonSettingBundle("Error Bundle Button", listener = {
                            Toast.makeText(this, "Custom Action", Toast.LENGTH_SHORT).show()
                        }))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
