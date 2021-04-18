package com.appdevlab.myapplication.Lab;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * Implementation of App Widget functionality.
 */
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class StudentDetails extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.student_details);
        setRemoteAdapterAndOnClick(context, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.student_details);
            setRemoteAdapterAndOnClick(context, views);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context,"onEnabled called", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context,"onDisabled called", Toast.LENGTH_LONG).show();
    }

    private static void setRemoteAdapterAndOnClick(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setComponent(new ComponentName("com.appdevlab.myapplication.Lab",
                    "com.appdevlab.myapplication.Lab.MainActivity"));
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.openApp, pendingIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(),
                    "There was a problem loading the application: ",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
