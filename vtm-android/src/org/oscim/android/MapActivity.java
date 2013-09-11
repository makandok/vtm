/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.android;

import org.oscim.core.GeoPoint;
import org.oscim.core.MapPosition;
import org.oscim.map.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * MapActivity is the abstract base class which must be extended in order to use
 * a {@link Map}. There are no abstract methods in this implementation which
 * subclasses need to override and no API key or registration is required.
 * <p>
 * A subclass may create a MapView either via one of the MapView constructors or
 * by inflating an XML layout file. It is possible to use more than one MapView
 * at the same time.
 * <p>
 * When the MapActivity is shut down, the current center position, zoom level
 * and map file of the MapView are saved in a preferences file and restored in
 * the next startup process.
 */
public abstract class MapActivity extends Activity {
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_MAP_SCALE = "map_scale";

	private static final String PREFERENCES_FILE = "MapActivity";
	//private static final String KEY_THEME = "Theme";

	private static boolean containsViewport(SharedPreferences sharedPreferences) {
		return sharedPreferences.contains(KEY_LATITUDE)
				&& sharedPreferences.contains(KEY_LONGITUDE)
				&& sharedPreferences.contains(KEY_MAP_SCALE);
	}

	protected Map mMap;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMap.destroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

		Editor editor = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE).edit();
		editor.clear();

		// save the map position
		MapPosition mapPosition = new MapPosition();

		mMap.getViewport().getMapPosition(mapPosition);

		GeoPoint geoPoint = mapPosition.getGeoPoint();

		editor.putInt(KEY_LATITUDE, geoPoint.latitudeE6);
		editor.putInt(KEY_LONGITUDE, geoPoint.longitudeE6);
		editor.putFloat(KEY_MAP_SCALE, (float)mapPosition.scale);

		//editor.putString(KEY_THEME, mMap.getRenderTheme());

		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * This method is called once by each MapView during its setup process.
	 *
	 * @param map
	 *            the calling MapView.
	 */
	public final void registerMapView(Map map) {
		mMap = map;

		SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE,
				MODE_PRIVATE);

		if (containsViewport(sharedPreferences)) {
			// get and set the map position and zoom level
			int latitudeE6 = sharedPreferences.getInt(KEY_LATITUDE, 0);
			int longitudeE6 = sharedPreferences.getInt(KEY_LONGITUDE, 0);
			float scale = sharedPreferences.getFloat(KEY_MAP_SCALE, 1);


			MapPosition mapPosition = new MapPosition();
			mapPosition.setPosition(latitudeE6 / 1E6, longitudeE6 / 1E6);
			mapPosition.setScale(scale);

			mMap.setMapPosition(mapPosition);
		}

		//String theme = sharedPreferences.getString(KEY_THEME,
		//		InternalRenderTheme.DEFAULT.name());

//		if (theme.startsWith("/")) {
//			try {
//				map.setRenderTheme(theme);
//			} catch (FileNotFoundException e) {
//				map.setRenderTheme(InternalRenderTheme.DEFAULT);
//			}
//		} else {
//			try {
//				map.setRenderTheme(InternalRenderTheme.valueOf(theme));
//			} catch (IllegalArgumentException e) {
//				map.setRenderTheme(InternalRenderTheme.DEFAULT);
//			}
//		}
	}
}
