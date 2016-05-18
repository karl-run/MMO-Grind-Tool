package net.hermith.efficientgrinder2.activities.economy;

import net.hermith.efficientgrinder2.R;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EconomyResult extends SherlockActivity implements OnClickListener {
    private String resTop, resMid, resBot, game, location;
    private TextView top, mid, bot;
    private Button ok, lastruns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        getBundle();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.economy);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setIcon(R.drawable.ic_economy_clean);

        inflater.inflate(R.menu.menu_results, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d("" + item.getTitle(), "" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String pre = "I just ";
                String mid = resMid.substring(0, resMid.lastIndexOf("minute") + 6);
                mid = mid.replace("<b>", "");
                mid = mid.replace("<big>", "");
                String post = "";
                if (!game.equals("") && !location.equals("")) {
                    post = " at " + location + " in " + game;
                } else if (!location.equals("")) {
                    post = " at " + location;
                } else if (!game.equals("")) {
                    post = " in " + game;
                }
                String shareBody = pre + mid + post;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            case R.id.settings:

                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void getBundle() {
        Bundle bund = getIntent().getExtras();
        resTop = bund.getString("top");
        resMid = bund.getString("mid");
        resBot = bund.getString("bot");
        game = bund.getString("game");
        location = bund.getString("location");
        game = game.replace(":", "").trim();
        location = location.replace(":", "").trim();
        Log.d("After", resTop + resMid + resBot);
    }

    private void init() {
        top = (TextView) findViewById(R.id.tvExpResultTop);
        mid = (TextView) findViewById(R.id.tvExpResultPercent);
        bot = (TextView) findViewById(R.id.tvExpResultBot);

        ok = (Button) findViewById(R.id.bExpResultOK);
        lastruns = (Button) findViewById(R.id.bExpResultLastRuns);

        ok.setOnClickListener(this);
        lastruns.setOnClickListener(this);

        top.setText(Html.fromHtml(resTop));
        mid.setText(Html.fromHtml(resMid)); // mid.setText(mid);
        bot.setText(Html.fromHtml(resBot));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bExpResultOK:
                finish();
                break;
            case R.id.bExpResultLastRuns:
                Intent i = new Intent(EconomyResult.this, EconomyLastRuns.class);
                startActivity(i);
                finish();
        }

    }

}