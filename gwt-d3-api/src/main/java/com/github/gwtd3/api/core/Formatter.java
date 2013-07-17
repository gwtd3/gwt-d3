/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.gwtd3.api.core;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Allows a number to be formatted as a String.
 * <p>
 * {@link Formatter} instances can be created with {@link D3#format(String)} or
 * with the {@link #create(String)} method.
 * <p>
 * The format specifier is modeled after <a href="http://docs.python.org/release/3.1.3/library/string.html#formatspec">Python 3.1's built-in format
 * specification mini-language</a>.
 * <p>
 * The general form of a specifier is <code>[​[fill]align][sign][#][0][width][,][.precision][type].</code>
 * <p>
 * The <code>fill</code> can be any character other than "{" or "}". The presence of a fill character is signaled by the character following it, which must be one of the align options.
 * <p>
 * The <code>align</code> can be:
 * <p>
 * <ul>
 * <li>("<") Forces the field to be left-aligned within the available space.
 * <li>(">") Forces the field to be right-aligned within the available space. (This is the default).
 * <li>("^") Forces the field to be centered within the available space.
 * </ul>
 * The <code>sign</code> can be:
 * <p>
 * <ul>
 * <li>plus ("+") - a sign should be used for both positive and negative numbers.
 * <li>minus ("-") - a sign should be used only for negative numbers. (This is the default.)
 * <li>space (" ") - a leading space should be used on positive numbers, and a minus sign on negative numbers.
 * </ul>
 * <p>
 * The <code>"#"</code> option is only valid for binary, octal, or hexadecimal output. If present, it specifies that the output will be prefixed by "0b", "0o", or "0x", respectively.
 * <p>
 * The <code>"0"</code> option enables zero-padding.
 * <p>
 * The <code>width</code> defines the minimum field width. If not specified, then the width will be determined by the content.
 * <p>
 * The <code>comma (",")</code> option enables the use of a comma for a thousands separator.
 * <p>
 * The <code>precision</code> indicates how many digits should be displayed 
 * after the decimal point for a value formatted with types "f" and "%", 
 * or before and after the decimal point for a value formatted with types "g", "r" and "p".
 * <p>
 * The available <code>type</code> values are:
 * <ul>
 * <li>exponent ("e") - use Number.toExponential.
 * <li>general ("g") - use Number.toPrecision.
 * <li>fixed ("f") - use Number.toFixed.
 * <li>integer ("d") - use Number.toString, but ignore any non-integer values.
 * <li>rounded ("r") - like fixed, but round to precision significant digits.
 * <li>percentage ("%") - like fixed, but multiply by 100 and suffix with "%".
 * <li>rounded percentage ("p") - like rounded, but multiply by 100 and suffix with "%".
 * <li>binary ("b") - outputs the number in base 2.
 * <li>octal ("o") - outputs the number in base 8.
 * <li>hexadecimal ("x") - outputs the number in base 16, using lower-case letters for the digits above 9.
 * <li>hexadecimal ("X") - outputs the number in base 16, using upper-case letters for the digits above 9.
 * <li>character ("c") - converts the integer to the corresponding unicode character before printing.
 * <li>SI-prefix ("s") - like rounded, but with a unit suffixed such as "9.5M" for mega, or "1.00µ" for micro.
 * </ul>
 * The type "n" is also supported as shorthand for ",g".
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Formatter extends JavaScriptObject implements IsFunction {

	protected Formatter() {
		super();
	}

	/**
	 * Return a String representation of the specified number formatted
	 * according to the specifier of this {@link Formatter}.
	 * 
	 * @param d
	 *            the number to be formatted
	 * @return the formatted String representation of the number.
	 */
	public final native String format(double d)/*-{
		return this(d);
	}-*/;

	/**
	 * Create a new instance of {@link Formatter}.
	 * 
	 * @see Formatter javadoc
	 * @param specifier
	 * @return the {@link Formatter} instance.
	 */
	public static Formatter create(final String specifier) {
		return D3.format(specifier);
	}
}
