package com.org.nic.ts;

import android.content.Intent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.org.nic.ts.custom.Utility;
//import com.org.nic.ts.tmatsya.force_close.ExceptionHandler;

public class SelectionPage extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout fish_seed_stocking_linear, fish_seed_stocking_previous_dates_linear, prawn_seed_stocking_linear,
            component_grounding_linear;
    private ImageButton quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);
       // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        fish_seed_stocking_linear = findViewById(R.id.fish_seed_stocking_linear);
        fish_seed_stocking_previous_dates_linear = findViewById(R.id.fish_seed_stocking_previous_dates_linear);
        prawn_seed_stocking_linear = findViewById(R.id.prawn_seed_stocking_linear);
        component_grounding_linear = findViewById(R.id.component_grounding_linear);
        quitBtn = findViewById(R.id.quit_button);

        quitBtn.setOnClickListener(this);
        fish_seed_stocking_linear.setOnClickListener(this);
        prawn_seed_stocking_linear.setOnClickListener(this);
        component_grounding_linear.setOnClickListener(this);
        fish_seed_stocking_previous_dates_linear.setOnClickListener(this);

        component_grounding_linear.setEnabled(false);
        component_grounding_linear.setClickable(false);
    }

    @Override
    public void onClick(View v) {

        if (v == quitBtn) {
            Utility.callSignOutAlert(SelectionPage.this);
        }

        if (v == fish_seed_stocking_linear) {
            startActivity(new Intent(SelectionPage.this, FishSeedStockingNavigation.class));
            finish();
        }
       /* if (v == fish_seed_stocking_previous_dates_linear) {
            startActivity(new Intent(SelectionPage.this, FishSeedStockingPreviousDatesNav.class));
            finish();
        }

        if (v == prawn_seed_stocking_linear) {
            startActivity(new Intent(SelectionPage.this, PrawnSeedStockingNavigation.class));
            finish();
        }

        if (v == component_grounding_linear) {
            startActivity(new Intent(SelectionPage.this, MainNavigation.class));
            finish();
        }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Utility.callSignOutAlert(SelectionPage.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {

            Utility.callSignOutAlert(SelectionPage.this);
        }

        return super.onKeyDown(keyCode, event);
    }
}
