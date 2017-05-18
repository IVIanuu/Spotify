package com.ivianuu.spotify.helper;

import com.ivianuu.spotify.api.models.Album;
import com.ivianuu.spotify.api.models.AlbumSimple;
import com.ivianuu.spotify.api.models.Artist;
import com.ivianuu.spotify.api.models.Category;
import com.ivianuu.spotify.api.models.Image;
import com.ivianuu.spotify.api.models.Playlist;
import com.ivianuu.spotify.api.models.PlaylistBase;
import com.ivianuu.spotify.api.models.PlaylistSimple;
import com.ivianuu.spotify.api.models.PlaylistTrack;
import com.ivianuu.spotify.api.models.SavedTrack;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.UserPrivate;
import com.ivianuu.spotify.api.models.UserPublic;

import java.util.List;

public class ImageHelper {

    public static final int SMALL = 2;
    public static final int MIDDLE = 1;
    public static final int BIG = 0;

    public static String getImage(Object object) {
        return getImage(object, MIDDLE);
    }

    public static String getImage(Object object, int size) {
        if (object instanceof Track) {
            return getImageInternal(((Track) object).album.images, size);
        } else if (object instanceof SavedTrack) {
            return getImageInternal(((SavedTrack) object).track.album.images, size);
        } else if (object instanceof PlaylistTrack) {
            return getImageInternal(((PlaylistTrack) object).track.album.images, size);
        } else if (object instanceof Artist) {
            return getImageInternal(((Artist) object).images, size);
        } else if (object instanceof AlbumSimple) {
            return getImageInternal(((AlbumSimple) object).images, size);
        } else if (object instanceof PlaylistBase) {
            return getImageInternal(((PlaylistBase) object).images, size);
        } else if (object instanceof UserPublic) {
            return getImageInternal(((UserPublic) object).images, size);
        } else if (object instanceof Category) {
            return getImageInternal(((Category) object).icons, size);
        } else {
            throw new IllegalArgumentException("unsupported type");
        }
    }

    private static String getImageInternal(List<Image> images, int size) {
        if (images != null && !images.isEmpty()) {
            if (size < images.size()) {
                return images.get(size).url;
            } else if (size >= 0) {
                return getImageInternal(images, size - 1);
            }
        }

        return "";
    }
}
