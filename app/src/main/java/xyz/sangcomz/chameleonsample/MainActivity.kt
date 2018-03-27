package xyz.sangcomz.chameleonsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import xyz.sangcomz.chameleon.Chameleon

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root.showState(Chameleon.STATE.LOADING)
        root.setEmptyButtonClickListener { Toast.makeText(this, "Empty Button!", Toast.LENGTH_LONG).show() }
        root.setErrorButtonClickListener { Toast.makeText(this, "Error Button!", Toast.LENGTH_LONG).show() }

        setChameleonList()
    }

    private fun setChameleonList() {
        rv_main_list.adapter = ChameleonAdapter(getChameleons())
        rv_main_list.layoutManager = LinearLayoutManager(this)
        rv_main_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_content -> {
                root.showState(Chameleon.STATE.CONTENT)
            }
            R.id.menu_loading -> {
                root.showState(Chameleon.STATE.LOADING)
            }
            R.id.menu_empty -> {
                root.showState(Chameleon.STATE.EMPTY)
            }
            R.id.menu_error -> {
                root.showState(Chameleon.STATE.ERROR)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
