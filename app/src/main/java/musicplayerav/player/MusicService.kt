package musicplayerav.player

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import musicplayerav.MainActivity
import musicplayerav.R


class MusicService() : Service() {

    private var mediaPlayer: MediaPlayer? = null

    companion object {
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_SKIP = "ACTION_SKIP"
        const val ACTION_REWIND = "ACTION_REWIND"
        const val EXTRA_MP3_URL = "EXTRA_MP3_URL"
        const val CURRENT_TRACK_ID = "CURRENT_TRACK_ID"
        const val REWIND_TIME = "REWIND_TIME"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MUSIC_CHANNEL"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()


    }

    private var currentTrackId:Long? = -1
    private var currentMp3Url: String? = ""


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Если MediaPlayer ещё не инициализирован, пробуем инициализировать его по ссылке
        val mp3Url = intent?.getStringExtra(EXTRA_MP3_URL)
        currentMp3Url = mp3Url
        val newTrackId = intent?.getLongExtra(CURRENT_TRACK_ID,-2)
        if (mediaPlayer == null) {
            currentTrackId = intent?.getLongExtra(CURRENT_TRACK_ID,-1)
            if (mp3Url != null) {
                loadSourceToMediaPlayer(mp3Url)
            }
        } else {
            // Управление плеером по командам
            when (intent?.action) {
                ACTION_PLAY -> {
                    currentMp3Url = mp3Url
                    if (!mediaPlayer!!.isPlaying) {
                        if (currentTrackId == newTrackId){ // чтобы при включение того же трека с паузы воспроизводилось с нужного места
                            mediaPlayer?.start()
                        }else{
                            mediaPlayer?.stop()
                            currentTrackId = newTrackId // запустили новую песню
                            loadSourceToMediaPlayer(mp3Url)
                            mediaPlayer?.start()
                        }
                        updateNotification()
                    }else{
                        mediaPlayer?.stop()
                        currentTrackId = newTrackId // Запустили новую песню
                        loadSourceToMediaPlayer(mp3Url)
                        updateNotification()
                        mediaPlayer?.start()

                    }
                }
                ACTION_PAUSE -> {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.pause()
                        updateNotification()
                    }
                }
                ACTION_REWIND -> {
                    val newTimePosition = intent.getIntExtra(REWIND_TIME, -3);
                    mediaPlayer?.seekTo(newTimePosition)
                }
            }
        }
        // Запускаем сервис в режиме foreground для стабильной работы в фоне
        startForeground(NOTIFICATION_ID, buildNotification())
        return START_STICKY
    }

    private fun loadSourceToMediaPlayer(mp3Url: String?) {
        if (currentMp3Url!=null){
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                Log.d("iiiiii",currentMp3Url.toString() )


                setDataSource(currentMp3Url)
                isLooping = false

                setOnPreparedListener {
                    it.start()
                    updateNotification()
                }
                setOnCompletionListener {
                    // Логика по окончанию воспроизведения, например, обновление уведомления или переход к следующему треку
                    updateNotification()
                }
                prepareAsync()
            }
        }


    }

    private fun resetMediaPlayer() {
        mediaPlayer?.apply {
            stop()
            reset()
            release()
        }
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun buildNotification(): Notification {
        // Intent для перехода в активность приложения
        val mainIntent = Intent(this, MainActivity::class.java)
        val pendingMainIntent = PendingIntent.getActivity(
            this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE
        )

        // PendingIntent для кнопки воспроизведения
        val playIntent = PendingIntent.getService(

            this, 0,
            Intent(this, MusicService::class.java).apply {
                action = ACTION_PLAY
                putExtra(EXTRA_MP3_URL, currentMp3Url)  // добавьте здесь mp3Url
                putExtra(CURRENT_TRACK_ID, currentTrackId)
                                                         },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // PendingIntent для кнопки паузы
        val pauseIntent = PendingIntent.getService(
            this, 1,
            Intent(this, MusicService::class.java).apply { action = ACTION_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )


        // Построение уведомления с кнопками управления
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MusicPlayerAV")
            .setContentText("Играет музыка")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingMainIntent)
            .addAction(android.R.drawable.ic_media_pause, "Пауза", pauseIntent)
            .addAction(android.R.drawable.ic_media_play, "Продолжить", playIntent)
            //.addAction(android.R.drawable.ic_media_ff, "Пропустить", skipIntent) // Добавьте кнопку для перехода к следующему треку, если необходимо
            .setOngoing(mediaPlayer?.isPlaying ?: false)
            .build()
    }

    private fun updateNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(if (mediaPlayer?.isPlaying == true) presentation.R.drawable.pause_music_icon else presentation.R.drawable.play_music_icon)
            .setContentTitle("MusicPlayerAV")
            .setContentText("Играет музыка")
            .setContentIntent(getMainPendingIntent())
            .addAction(
                if (mediaPlayer?.isPlaying == true) presentation.R.drawable.pause_music_icon else presentation.R.drawable.play_music_icon,
                if (mediaPlayer?.isPlaying == true) "Пауза" else "Продолжить",
                if (mediaPlayer?.isPlaying == true) getActionPendingIntent(ACTION_PAUSE) else getActionPendingIntent(ACTION_PLAY)
            )

        // Обновляем уведомление через NotificationManager
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun getMainPendingIntent(): PendingIntent {
        // Создаем Intent для открытия главной Activity приложения
        val intent = Intent(this, MainActivity::class.java).apply {
            // Очищаем предыдущие экземпляры Activity и переиспользуем существующий, если он уже запущен
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        // PendingIntent для перехода в Activity с обновлением предыдущего Intent
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getActionPendingIntent(action: String): PendingIntent {
        // Создаем Intent для выполнения действия в сервисе (например, play, pause, rewind)
        val intent = Intent(this, MusicService::class.java).apply {
            this.action = action
        }
        // PendingIntent для вызова сервиса с заданным действием
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createNotificationChannel() {
        val channelName = "Music Playback"
        val channelDescription = "Channel for music playback controls"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}
