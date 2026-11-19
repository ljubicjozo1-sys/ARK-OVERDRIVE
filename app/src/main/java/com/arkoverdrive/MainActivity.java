package com.arkoverdrive;

import android.app.Activity;
import android.os.Bundle;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    LinearLayout root;
    int red = Color.rgb(229,57,53), white = Color.WHITE, muted = Color.LTGRAY;
    @Override public void onCreate(Bundle b) { super.onCreate(b); getWindow().setStatusBarColor(Color.rgb(9,9,9)); getWindow().setNavigationBarColor(Color.BLACK); showHome(); }
    TextView text(String s, int size, int color) { TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setPadding(0,8,0,8); return t; }
    void base(String title) { ScrollView sc=new ScrollView(this); root=new LinearLayout(this); root.setOrientation(1); root.setPadding(22,24,22,28); root.setBackgroundColor(Color.rgb(12,12,14)); sc.addView(root); setContentView(sc); TextView h=text("ARK OVERDRIVE",26,red); h.setTypeface(null,Typeface.BOLD); root.addView(h); root.addView(text(title,16,muted)); }
    Button btn(String label, Runnable action) { Button b=new Button(this); b.setText(label); b.setTextColor(white); b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(45,25,27))); b.setOnClickListener(v->action.run()); root.addView(b); return b; }
    EditText field(String hint) { EditText e=new EditText(this); e.setSingleLine(true); e.setHint(hint); e.setHintTextColor(Color.GRAY); e.setTextColor(white); root.addView(e); return e; }
    void copy(String s) { ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("ARK command",s)); Toast.makeText(this,"Copied to clipboard",Toast.LENGTH_SHORT).show(); }
    void showHome() { base("ADMIN COMMAND COMPANION • LOCAL TOOL"); root.addView(text("Build commands for servers where you have permission. Commands are copied only; this app does not connect to or modify ARK.",14,muted)); btn("DINO SPAWN COMMAND",()->dino()); btn("ITEM COMMAND",()->item()); btn("TELEPORT COMMAND",()->teleport()); btn("COMMAND REFERENCE",()->reference()); btn("ABOUT & LIMITATIONS",()->about()); }
    void dino() { base("DINO COMMAND BUILDER"); EditText path=field("Dino blueprint path (server-specific)"); EditText level=field("Level (number)"); btn("COPY SPAWN COMMAND",()->{ String p=path.getText().toString().trim(), l=level.getText().toString().trim(); if(p.isEmpty()||l.isEmpty()){Toast.makeText(this,"Enter blueprint path and level",Toast.LENGTH_SHORT).show();return;} copy("cheat SpawnDino \\\""+p+"\\\" 500 0 0 "+l); }); btn("BACK",()->showHome()); }
    void item() { base("ITEM COMMAND BUILDER"); EditText path=field("Item blueprint path"); EditText qty=field("Quantity"); btn("COPY ITEM COMMAND",()->{String p=path.getText().toString().trim(),q=qty.getText().toString().trim(); if(p.isEmpty()||q.isEmpty()){Toast.makeText(this,"Enter blueprint path and quantity",Toast.LENGTH_SHORT).show();return;} copy("cheat GiveItem \\\""+p+"\\\" 1 "+q+" 0");}); btn("BACK",()->showHome()); }
    void teleport() { base("TELEPORT COMMAND BUILDER"); EditText x=field("X coordinate"), y=field("Y coordinate"), z=field("Z coordinate"); btn("COPY TELEPORT COMMAND",()->{if(x.length()==0||y.length()==0||z.length()==0){Toast.makeText(this,"Enter all coordinates",Toast.LENGTH_SHORT).show();return;} copy("cheat SetPlayerPos "+x.getText()+" "+y.getText()+" "+z.getText());}); btn("BACK",()->showHome()); }
    void reference() { base("COMMAND REFERENCE"); root.addView(text("Examples only; command names and syntax depend on the ARK Mobile 2018 build and server configuration. Ask the server owner which commands are supported.",15,muted)); root.addView(text("SpawnDino • GiveItem • SetPlayerPos",18,white)); btn("BACK",()->showHome()); }
    void about() { base("ABOUT"); root.addView(text("ARK OVERDRIVE is an offline command builder. It does not inject into the game, read game memory, bypass anti-cheat, reveal hidden players, or grant unauthorized admin privileges. Use only on servers where you have permission. ARK Mobile 2018 may not support desktop command syntax.",15,muted)); btn("BACK",()->showHome()); }
}
