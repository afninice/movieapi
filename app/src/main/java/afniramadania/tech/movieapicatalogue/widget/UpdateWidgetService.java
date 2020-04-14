package afniramadania.tech.movieapicatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class UpdateWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewFactory(this.getApplicationContext(), intent);
    }



}
