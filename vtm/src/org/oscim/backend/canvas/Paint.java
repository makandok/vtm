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
package org.oscim.backend.canvas;

/**
 * The Interface Paint.
 */
public interface Paint {

	public static enum Align {
		CENTER, LEFT, RIGHT;
	}

	public enum Cap {
		BUTT, ROUND, SQUARE;
	}

	public enum Style {
		FILL, STROKE
	}

	public enum FontFamily {
		DEFAULT, DEFAULT_BOLD, MONOSPACE, SANS_SERIF, SERIF;
	}

	public enum FontStyle {
		BOLD, BOLD_ITALIC, ITALIC, NORMAL;
	}

	int getColor();

	int getTextHeight(String text);

	int getTextWidth(String text);

	void setBitmapShader(Bitmap bitmap);

	void setColor(int color);

	void setDashPathEffect(float[] strokeDasharray);

	void setStrokeCap(Cap cap);

	void setStrokeWidth(float width);

	void setStyle(Style style);

	void setTextAlign(Align align);

	void setTextSize(float textSize);

	void setTypeface(FontFamily fontFamily, FontStyle fontStyle);

	float measureText(String text);

	float getFontHeight();

	float getFontDescent();
}
