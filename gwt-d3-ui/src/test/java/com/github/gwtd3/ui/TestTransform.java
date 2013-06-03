package com.github.gwtd3.ui;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.ui.svg.Transform;
import com.github.gwtd3.ui.svg.TransformList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Selection.class })
public class TestTransform {

	@Test
	public void testTransform() {
		TransformList l = new TransformList();
		TransformList.parse(l, "");
		assertThat(l.isEmpty()).isTrue();
		assertThat(l.toString()).isEqualTo(null);
		//
		l.push(Transform.translate(5, 4));
		assertThat(l.toString()).isEqualTo("translate(5,4)");
		//
		l.push(Transform.translate(10));
		assertThat(l.toString()).isEqualTo("translate(5,4) translate(10)");

		l.clear();
		assertThat(l.isEmpty()).isTrue();

	}
}
