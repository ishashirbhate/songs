import java.util.*;

class Song {
    String name;
    Song prev;
    Song next;

    public Song(String name) {
        this.name = name;
    }
}

class RecentlyPlayedSongs {
    int capacity;
    Map<String, Song> songMap;
    Song head;
    Song tail;

    public RecentlyPlayedSongs(int capacity) {
        this.capacity = capacity;
        this.songMap = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    public void addSong(String user, String song) {
        Song currentSong = songMap.get(user);
        if (currentSong != null) {
            // Remove the current song from the list
            removeSong(currentSong);
        } else if (songMap.size() >= capacity) {
            // Remove the least recently played song from the list
            removeSong(tail);
        }
        // Add the new song to the front of the list
        Song newSong = new Song(song);
        newSong.next = head;
        if (head != null) {
            head.prev = newSong;
        }
        head = newSong;
        if (tail == null) {
            tail = head;
        }
        songMap.put(user, newSong);
    }

    public List<String> getRecentlyPlayedSongs(String user) {
        List<String> songList = new ArrayList<>();
        Song currentSong = songMap.get(user);
        while (currentSong != null) {
            songList.add(currentSong.name);
            currentSong = currentSong.next;
        }
        return songList;
    }

    private void removeSong(Song song) {
        if (song == null) {
            return;
        }
        if (song.prev != null) {
            song.prev.next = song.next;
        } else {
            head = song.next;
        }
        if (song.next != null) {
            song.next.prev = song.prev;
        } else {
            tail = song.prev;
        }
        songMap.remove(song.name);
    }
}

class Main {
    public static void main(String[] args) {
        RecentlyPlayedSongs store = new RecentlyPlayedSongs(3);
        store.addSong("user1", "S1");
        store.addSong("user1", "S2");
        store.addSong("user1", "S3");
        System.out.println(store.getRecentlyPlayedSongs("user1")); // [S3, S2, S1]
        store.addSong("user1", "S4");
        System.out.println(store.getRecentlyPlayedSongs("user1")); // [S4, S2, S3]
        store.addSong("user1", "S2");
        System.out.println(store.getRecentlyPlayedSongs("user1")); // [S2, S4, S3]
        store.addSong("user1", "S1");
        System.out.println(store.getRecentlyPlayedSongs("user1")); // [S1, S2, S4]
    }
}
