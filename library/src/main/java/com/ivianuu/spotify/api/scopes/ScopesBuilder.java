package com.ivianuu.spotify.api.scopes;

import java.util.ArrayList;
import java.util.List;

public class ScopesBuilder {

    private List<String> mScopes = new ArrayList<>();

    public ScopesBuilder() {

    }

    public ScopesBuilder withPlaylistReadPrivate() {
        mScopes.add(Scopes.PLAYLIST_READ_PRIVATE);
        return this;
    }

    public ScopesBuilder withPlaylistReadCollaborative() {
        mScopes.add(Scopes.PLAYLIST_READ_COLLABORATIVE);
        return this;
    }

    public ScopesBuilder withPlaylistModifyPublic() {
        mScopes.add(Scopes.PLAYLIST_MODIFY_PUBLIC);
        return this;
    }

    public ScopesBuilder withPlaylistModifyPrivate() {
        mScopes.add(Scopes.PLAYLIST_MODIFY_PRIVATE);
        return this;
    }

    public ScopesBuilder withStreaming() {
        mScopes.add(Scopes.STREAMING);
        return this;
    }

    public ScopesBuilder withUserFollowModify() {
        mScopes.add(Scopes.USER_FOLLOW_MODIFY);
        return this;
    }

    public ScopesBuilder withUserFollowRead() {
        mScopes.add(Scopes.USER_FOLLOW_READ);
        return this;
    }

    public ScopesBuilder withUserLibraryModify() {
        mScopes.add(Scopes.USER_LIBRARY_MODIFY);
        return this;
    }

    public ScopesBuilder withUserLibraryRead() {
        mScopes.add(Scopes.USER_LIBRARY_READ);
        return this;
    }

    public ScopesBuilder withUserReadPrivate() {
        mScopes.add(Scopes.USER_READ_PRIVATE);
        return this;
    }

    public ScopesBuilder withUserReadBirthdate() {
        mScopes.add(Scopes.USER_READ_BIRTHDATE);
        return this;
    }

    public ScopesBuilder withUserReadEmail() {
        mScopes.add(Scopes.USER_READ_EMAIL);
        return this;
    }

    public ScopesBuilder withUserTopRead() {
        mScopes.add(Scopes.USER_TOP_READ);
        return this;
    }

    public ScopesBuilder withUserReadRecentlyPlayed() {
        mScopes.add(Scopes.USER_READ_RECENTLY_PLAYED);
        return this;
    }

    public ScopesBuilder withUserModifyPlaybackState() {
        mScopes.add(Scopes.USER_MODIFY_PLAYBACK_STATE);
        return this;
    }

    public ScopesBuilder withUserReadPlaybackState() {
        mScopes.add(Scopes.USER_READ_PLAYBACK_STATE);
        return this;
    }

    public ScopesBuilder withCustomScope(String scope) {
        mScopes.add(scope);
        return this;
    }

    public ScopesBuilder withAllScopes() {
        withPlaylistReadPrivate();
        withPlaylistReadCollaborative();
        withPlaylistModifyPublic();
        withPlaylistModifyPrivate();
        withStreaming();
        withUserFollowModify();
        withUserFollowRead();
        withUserLibraryModify();
        withUserLibraryRead();
        withUserReadPrivate();
        withUserReadBirthdate();
        withUserReadEmail();
        withUserTopRead();
        withUserReadRecentlyPlayed();
        withUserReadPlaybackState();
        withUserModifyPlaybackState();
        return this;
    }

    public String[] build() {
        String[] scopes = new String[mScopes.size()];
        for (int i = 0; i < mScopes.size(); i++) {
            scopes[i] = mScopes.get(i);
        }

        return scopes;
    }
}
