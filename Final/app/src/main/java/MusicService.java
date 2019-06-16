import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    public final IBinder binder = new MyBinder();
    private String[] musicDir = new String[]{
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/仙剑奇侠传六-主题曲-《誓言成晖》.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/仙剑奇侠传六-主题曲-《剑客不能说》.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/仙剑奇侠传六-主题曲-《镜中人》.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/仙剑奇侠传六-主题曲-《浪花》.mp3"};
    private int musicIndex = 1;
    public MediaPlayer mp = new MediaPlayer();
    public MusicService() {
        try {
            musicIndex = 1;
            mp.setDataSource(musicDir[musicIndex]);
            mp.prepare();
        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
            e.printStackTrace();
        }
    }
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

        public void playOrPause() {
            if (mp.isPlaying()) {
                mp.pause();
            } else {
                mp.start();
            }
        }

        public void stop() {
            if (mp != null) {
                mp.stop();
                try {
                    mp.prepare();
                    mp.seekTo(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void nextMusic() {
            if (mp != null && musicIndex < 3) {
                mp.stop();
                try {
                    mp.reset();
                    mp.setDataSource(musicDir[musicIndex + 1]);
                    musicIndex++;
                    mp.prepare();
                    mp.seekTo(0);
                    mp.start();
                } catch (Exception e) {
                    Log.d("hint", "can't jump next music");
                    e.printStackTrace();
                }
            }
        }

        public void preMusic() {
            if (mp != null && musicIndex > 0) {
                mp.stop();
                try {
                    mp.reset();
                    mp.setDataSource(musicDir[musicIndex - 1]);
                    musicIndex--;
                    mp.prepare();
                    mp.seekTo(0);
                    mp.start();
                } catch (Exception e) {
                    Log.d("hint", "can't jump pre music");
                    e.printStackTrace();
                }
            }
        }

    }

