/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 * Copyright 2013 Hannes Janetzek
 *
 * This file is part of the OpenScienceMap project (http://www.opensciencemap.org).
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
package org.oscim.layers.tile.bitmap;

import java.net.MalformedURLException;
import java.net.URL;

import org.oscim.core.Tile;

public class StamenTonerTiles extends AbstractTileSource {
	public static final StamenTonerTiles INSTANCE = new StamenTonerTiles(".tile.stamen.com", 80);
	private static final int PARALLEL_REQUESTS_LIMIT = 8;
	private static final String PROTOCOL = "http";
	private static final int ZOOM_LEVEL_MAX = 16;
	private static final int ZOOM_LEVEL_MIN = 0;

	public StamenTonerTiles(String hostName, int port) {
		super(hostName, port);
	}

	@Override
	public int getParallelRequestsLimit() {
		return PARALLEL_REQUESTS_LIMIT;
	}

	private char[] subdomains = { 'a', 'b', 'c', 'd' };
	private int curDomain = 0;

	@Override
	public URL getTileUrl(Tile tile) throws MalformedURLException {
		StringBuilder stringBuilder = new StringBuilder(32);

		stringBuilder.append("/toner/");
		stringBuilder.append(tile.zoomLevel);
		stringBuilder.append('/');
		stringBuilder.append(tile.tileX);
		stringBuilder.append('/');
		stringBuilder.append(tile.tileY);
		stringBuilder.append(".png");

		curDomain = (curDomain++) % 4;

		return new URL(PROTOCOL, subdomains[curDomain] + hostName, port, stringBuilder.toString());
	}

	@Override
	public byte getZoomLevelMax() {
		return ZOOM_LEVEL_MAX;
	}

	@Override
	public byte getZoomLevelMin() {
		return ZOOM_LEVEL_MIN;
	}
}
