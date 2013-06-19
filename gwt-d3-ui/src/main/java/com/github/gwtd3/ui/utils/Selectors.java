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
/**
 * 
 */
package com.github.gwtd3.ui.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * EXPERIMENTAL
 * 
 * Convenient methods to create valid CSS3 selectors.
 * <p>
 * According to official specs, all selectors are case-insensitive;
 * 
 * @see <a href="http://www.w3.org/TR/selectors/">official CSS3 selectors
 *      specifications</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Selectors {

	private static final String ANY = "*";
	private static final String EMPTY = "";

	public static void main(final String[] args) {
		// creation of sequence
		Select.elements("div").whoseAttribute("name").exists();
		Select.elements("div").whoseAttribute("name").isEqualsTo("foo");
		Select.elements("a").whoseAttribute("title").contains("foo").and().beingActive();
		Select.elements("div").whoseAttribute("class").hasASpaceSeparatedValueEqualsTo("bar").and().beingActive();
		Select.elements("div").havingClass("bar").and().beingFocused();
		Select.elements("p").butOnlyTheFirstLine();
		Select.elements().being(PseudoElement.firstLinePseudoElement()).havingID("blah");
		// all the elements under the svg: namespace
		Select.elements(Universal.inNamespaceUniversal("svg"));
		Select.elements().havingClasses("bar", "foo");
		// elements that not has attribute "name" but
		// FIXME: Select.elements().hasNoAttr("name").;

		// pseudo elemeent
		Select.elements("p").butOnlyTheFirstLetter();
		Select.elements("p").butOnlyTheFirstLine();
		Select.theFirstLetterOfElements("p");
		Select.theFirstLineOfElements("p");

		// combinators
		// select the <a> somewhere inside <li> which have <ul> as direct
		// parent.
		Select.elements("a").havingForAncestors("li").whoseAttribute("class").exists().havingForParents("ul");
		Select.elements("p").havingForPreviousSiblings("div");
		Select.elements("p").havingForPreviousAdjacentSiblings("div");

		// union of sequence / group of selectors
		Select.elements("div").whoseAttribute("title").exists()
				.orElements().elements("p").whoseAttribute("title").exists().and().butOnlyTheFirstLine()
				.orElements().elements("a").havingClass("foo");

		// complex syntax equivalent to:
		// div.contents > p[title*=par]:first-child::first-letter,
		// *:visited::first-letter, *:link::first-letter;
		// in other words:
		// it selects the first letter of p elements with a title attribute
		// containing "par" which are the first children
		// of a div parent with a class "contents", and it will also select the
		// first letter of any link (visited or unvisited).
		Select.theFirstLetterOfElements("p").whoseAttribute("title").contains("par")
				.beingFirstChildren()
				.havingForParents("div").havingClass("contents")
				.orElements().butOnlyTheFirstLetter().and().beingVisitedLink()
				.orElements().butOnlyTheFirstLetter().and().beingUnvisitedLink();
	}

	public static class Select {
		public static class SelectorBuilder {
			private Sequence currentSequence = new Sequence();
			private Selector currentSelector = new Selector(currentSequence);
			private final SelectorGroup group = new SelectorGroup(currentSelector);

			/**
			 * 
			 */
			public SelectorBuilder butOnlyTheFirstLine() {
				currentSelector().setPseudoElement(PseudoElement.firstLinePseudoElement());
				return this;
			}

			/**
			 * Create a new sequence starting with the type selector specified
			 * by the given tag name, this new sequence defining a sibling
			 * immediately before the current selector.
			 * <p>
			 * In other words, the current selector defines elements that are
			 * siblings after the new selector, immediately adjacent to it.
			 * 
			 * @param tagName
			 *            the type selector to be used for the sibling before
			 *            the current selector.
			 * @return the selector
			 */
			public SelectorBuilder havingForPreviousAdjacentSiblings(final String tagName) {
				currentSequence = new Sequence(Type.inDefaultNamespaceType(tagName));
				currentSelector.insert(currentSequence, Combinator.ADJACENT_SIBLING);
				return this;
			}

			/**
			 * Create a new sequence starting with the type selector specified
			 * by the given tag name, this new sequence defining a sibling
			 * before the current selector.
			 * <p>
			 * In other words, the current selector defines elements that are
			 * siblings after the new selector, but not necessarily immediately
			 * adjacent to it.
			 * 
			 * @param tagName
			 *            the type selector to be used for the sibling before
			 *            the current selector.
			 * @return the selector
			 */
			public SelectorBuilder havingForPreviousSiblings(final String tagName) {
				currentSequence = new Sequence(Type.inDefaultNamespaceType(tagName));
				currentSelector.insert(currentSequence, Combinator.GENERAL_SIBLING);
				return this;
			}

			/**
			 * Create a new sequence starting with the type selector specified
			 * by the given tag name, this new sequence defining the immediate
			 * parent of the current selector.
			 * <p>
			 * In other words, the current selector defines elements that are
			 * direct children of the new selector.
			 * 
			 * @param tagName
			 *            the type selector to be used for the ancestor.
			 * @return the selector
			 */
			public SelectorBuilder havingForParents(final String tagName) {
				currentSequence = new Sequence(Type.inDefaultNamespaceType(tagName));
				currentSelector.insert(currentSequence, Combinator.CHILD);
				return this;
			}

			/**
			 * Create a new sequence starting with the type selector specified
			 * by the given tag name, this new sequence defining the ancestor of
			 * the current selector.
			 * <p>
			 * In other words, the current selector is defined as a descendant
			 * of the new selector.
			 * 
			 * @param tagName
			 *            the type selector to be used for the ancestor.
			 * @return the selector
			 */
			public SelectorBuilder havingForAncestors(final String tagName) {
				currentSequence = new Sequence(Type.inDefaultNamespaceType(tagName));
				currentSelector.insert(currentSequence, Combinator.DESCENDANT);
				return this;
			}

			/**
			 * @param string
			 * @return
			 */
			public SelectorBuilder elements(final String tagName) {
				currentSequence.setType(Type.inDefaultNamespaceType(tagName));
				return this;
			}

			/**
			 * 
			 */
			public SelectorBuilder butOnlyTheFirstLetter() {
				currentSelector().setPseudoElement(PseudoElement.firstLetterPseudoElement());
				return this;
			}

			public SelectorBuilder being(final PseudoElement pseudoElement) {
				currentSelector().setPseudoElement(pseudoElement);
				return this;
			}

			/**
			 * @return
			 */
			private Selector currentSelector() {
				return currentSelector;
			}

			// ======= changing sequence =======

			/**
			 * 
			 */
			private Sequence currentSequence() {
				return currentSequence;
			}

			/**
			 * 
			 */
			public SelectorBuilder and() {
				return this;
			}

			/**
			 * Close the current selector and append a new one in the selector
			 * group.
			 * 
			 * @return
			 */
			public SelectorBuilder orElements() {
				currentSequence = new Sequence();
				currentSelector = new Selector(currentSequence);
				group.append(currentSelector);
				return this;
			}

			// ======= adding simple selectors to the current sequence =======
			public AttributeSelectorBuilder whoseAttribute(final String name) {
				return whoseAttribute(QualifiedName.inDefaultNamespace(name));
			}

			public AttributeSelectorBuilder whoseAttribute(final QualifiedName name) {
				return new AttributeSelectorBuilder(this, name);
			}

			public SelectorBuilder havingClass(final String className) {
				this.currentSequence().append(Class.hasClass(className));
				return this;
			}

			public SelectorBuilder havingClasses(String... classNames) {
				for (String className : classNames) {
					havingClass(className);
				}
				return this;
			}

			public SelectorBuilder having(final Class classSelector) {
				this.currentSequence().append(classSelector);
				return this;
			}

			public SelectorBuilder havingID(final String id) {
				this.currentSequence().append(ID.hasID(id));
				return this;
			}

			public SelectorBuilder having(final ID idSelector) {
				this.currentSequence().append(idSelector);
				return this;
			}

			public SelectorBuilder having(final PseudoClass pseudoClass) {
				this.currentSequence().append(pseudoClass);
				return this;
			}

			public SelectorBuilder beingActive() {
				having(PseudoClass.activePseudoClass());
				return this;
			}

			public SelectorBuilder beingChecked() {
				having(PseudoClass.checkedPseudoClass());
				return this;
			}

			public SelectorBuilder beingEvenChildren() {
				having(PseudoClass.childEvenPseudoClass());
				return this;
			}

			public SelectorBuilder beingOddChildren() {
				having(PseudoClass.childOddPseudoClass());
				return this;
			}

			public SelectorBuilder beingNthChildren(final int index) {
				having(PseudoClass.childPseudoClass(index));
				return this;
			}

			public SelectorBuilder beingNthChildren(final int a, final int b) {
				having(PseudoClass.childPseudoClass(a, b));
				return this;
			}

			public SelectorBuilder beingDisabled() {
				having(PseudoClass.disabledPseudoClass());
				return this;
			}

			public SelectorBuilder beingEnabled() {
				having(PseudoClass.enabledPseudoClass());
				return this;
			}

			public SelectorBuilder beingEmpty() {
				having(PseudoClass.emptyPseudoClass());
				return this;
			}

			public SelectorBuilder beingFirstChildren() {
				having(PseudoClass.firstChildPseudoClass());
				return this;
			}

			public SelectorBuilder beingLastChildren() {
				having(PseudoClass.lastChildPseudoClass());
				return this;
			}

			public SelectorBuilder beingFirstChildrenOfType() {
				having(PseudoClass.firstOfTypePseudoClass());
				return this;
			}

			public SelectorBuilder beingLastChildrenOfType() {
				having(PseudoClass.lastOfTypePseudoClass());
				return this;
			}

			public SelectorBuilder beingFocused() {
				having(PseudoClass.focusPseudoClass());
				return this;
			}

			public SelectorBuilder beingHovered() {
				having(PseudoClass.hoverPseudoClass());
				return this;
			}

			public SelectorBuilder beingIndeterminated() {
				having(PseudoClass.indeterminatePseudoClass());
				return this;
			}

			public SelectorBuilder beingInLanguage(final String langOrPrefix) {
				having(PseudoClass.langPseudoClass(langOrPrefix));
				return this;
			}

			public SelectorBuilder beingUnvisitedLink() {
				having(PseudoClass.linkPseudoClass());
				return this;
			}

			public SelectorBuilder beingVisitedLink() {
				having(PseudoClass.visitedPseudoClass());
				return this;
			}

			public SelectorBuilder beingTheOnlyChildren() {
				having(PseudoClass.onlyChildPseudoClass());
				return this;
			}
		}

		// ======= selecting elements by type or universal=======

		public static SelectorBuilder elements() {
			return elements(Universal.inDefaultNamespace());
		}

		/**
		 * @param string
		 * @return
		 */
		public static SelectorBuilder theFirstLetterOfElements(final String tagName) {
			return elements(tagName).butOnlyTheFirstLetter();
		}

		/**
		 * @param string
		 * @return
		 */
		public static SelectorBuilder theFirstLineOfElements(final String tagName) {
			return elements(tagName).butOnlyTheFirstLine();
		}

		public static SelectorBuilder elements(final Universal universal) {
			SelectorBuilder s = new SelectorBuilder();
			s.currentSequence().setUniversal(universal);
			return s;
		}

		public static SelectorBuilder elements(final Type type) {
			SelectorBuilder s = new SelectorBuilder();
			s.currentSequence().setType(type);
			return s;
		}

		/**
		 * @param string
		 * @return
		 */
		public static SelectorBuilder elements(final String tagName) {
			return elements(Type.inDefaultNamespaceType(tagName));
		}

		/**
		 * @param string
		 * @return
		 */
		public static SelectorBuilder elements(final QualifiedName tagName) {
			return elements(Type.inNamespaceType(tagName));
		}

		public static class AttributeSelectorBuilder {
			private final QualifiedName qName;
			private final SelectorBuilder select;

			/**
			 * @param select
			 * @param name
			 */
			public AttributeSelectorBuilder(final SelectorBuilder select, final QualifiedName name) {
				this.qName = name;
				this.select = select;
			}

			/**
			 * the attribute exists
			 * 
			 * @return the select
			 */
			public SelectorBuilder exists() {
				appendAttributeSelector(Attribute.hasAttribute(qName));
				return select;
			}

			/**
			 * the attribute is equals to the value
			 * 
			 * @param the
			 *            value
			 * @return the select
			 */
			public SelectorBuilder isEqualsTo(final String value) {
				appendAttributeSelector(Attribute.valueEqualsAttribute(qName, value));
				return select;
			}

			/**
			 * the attribute is a list of whitespace separated values, one of
			 * which is equals to value.
			 * 
			 * @param value
			 * @return the select
			 */
			public SelectorBuilder hasASpaceSeparatedValueEqualsTo(final String value) {
				appendAttributeSelector(Attribute.hasValueEqualsAttribute(qName, value));
				return select;
			}

			/**
			 * the attribute is a list of hyphen separated values, the first
			 * value of which is equals to the given value; or the attribute is
			 * equals to the given value.
			 * 
			 * @param value
			 * @return
			 */
			public SelectorBuilder equalsOrStartWithHyphenTerminatedValue(final String value) {
				appendAttributeSelector(Attribute.hasValueEqualsAttribute(qName, value));
				return select;
			}

			public SelectorBuilder contains(final String value) {
				appendAttributeSelector(Attribute.containsAttribute(qName, value));
				return select;
			}

			public SelectorBuilder startsWith(final String value) {
				appendAttributeSelector(Attribute.startsWithAttribute(qName, value));
				return select;
			}

			public SelectorBuilder endsWith(final String value) {
				appendAttributeSelector(Attribute.endsWithAttribute(qName, value));
				return select;
			}

			private Sequence appendAttributeSelector(final Attribute selector) {
				return select.currentSequence().append(selector);
			}
		}

	}

	public static enum AttributeOperator {

		/**
		 * The attribute value is exactly equals to the operand 2.
		 */
		EQUALS("="),

		/**
		 * Starts with the operand 2.
		 */
		BEGINS_WITH("^="),
		/**
		 * Ends with the operand 2.
		 */
		ENDS_WITH("$="),
		/**
		 * Contains the operand 2.
		 */
		CONTAINS_WITH("*="),
		/**
		 * The attribute value is a list of comma separated values whose one is
		 * equals to the operand 2.
		 */
		CONTAINS_SPACE_SEPARATED_VALUE("~="),
		/**
		 * The attribute is exactly equals to the operand 2 or begins with the
		 * operand 2 and is immediately followed by an hyphen.
		 * 
		 */
		STARTS_WITH_HYPHEN_SEPARATED_VALUE("|=");

		private final String operator;

		private AttributeOperator(final String operator) {
			this.operator = operator;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return operator;
		}
	}

	public static enum Combinator {
		DESCENDANT(" "),
		CHILD(">"),
		ADJACENT_SIBLING("+"),
		GENERAL_SIBLING("-");
		private final String separator;

		private Combinator(final String separator) {
			this.separator = separator;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return separator;
		}
	}

	/**
	 * Comma separated list of selectors. Must have at least one selector.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class SelectorGroup {
		/**
		 * Separator for selectors inside the group.
		 * 
		 */
		private static final String SELECTOR_SEPARATOR = ",";

		private final List<Selector> list = new ArrayList<Selector>();

		/**
		 * Construct a new group starting with the given selector
		 * 
		 * @param the
		 *            first selector of the group.
		 * 
		 */
		public SelectorGroup(final Selector selector) {
			super();
			append(selector);
		}

		/**
		 * Append a new selector in this group.
		 * 
		 * @param selector
		 * @return
		 */
		public SelectorGroup append(final Selector selector) {
			if (selector == null) {
				throw new IllegalArgumentException("selector cannot be null");
			}
			list.add(selector);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String s = "";
			for (Selector selector : list) {
				s += selector.toString() + SelectorGroup.SELECTOR_SEPARATOR;
			}
			if (s.length() > 0) {
				s = s.substring(0, s.length() - 1);
			}
			return super.toString();
		}

	}

	/**
	 * A selector is a chain of sequences of simple selectors , each sequence
	 * being separated by combinators and optionnally terminated by a pseudo
	 * element.
	 * <p>
	 * At least one sequence or a pseudo element is required.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class Selector {
		/**
		 * Chain of sequences of simple selectors.
		 */
		private final List<Sequence> sequences = new ArrayList<Sequence>();
		/**
		 * Combinators between sequences
		 */
		private final List<Combinator> combinators = new ArrayList<Selectors.Combinator>();
		/**
		 * appended after the last sequence
		 */
		public PseudoElement pseudoElement;

		/**
		 * Construct a Selector with the given sequence.
		 * 
		 */
		public Selector(final Sequence sequence) {
			super();
			sequences.add(sequence);
		}

		/**
		 * @param currentSequence
		 * @param descendant
		 */
		public Selector insert(final Sequence sequence, final Combinator combinator) {
			this.combinators.add(0, combinator);
			this.sequences.add(0, sequence);
			return this;
		}

		/**
		 * Construct a Selector with the given pseudo element.
		 * 
		 * @param pseudoElement
		 */
		public Selector(final PseudoElement pseudoElement) {
			super();
			this.pseudoElement = pseudoElement;
		}

		/**
		 * Append the given sequence as a descendant of the last appended
		 * sequence.
		 * 
		 * @param descendant
		 * @return
		 */
		public Selector descendant(final Sequence descendant) {
			// whitespace separated: means descendant or children
			this.combinators.add(Combinator.DESCENDANT);
			this.sequences.add(descendant);
			return this;
		}

		/**
		 * Append the given sequence as a child of the last appended sequence.
		 * 
		 * @param child
		 * @return
		 */
		public Selector child(final Sequence child) {
			// > separated: means child
			this.combinators.add(Combinator.CHILD);
			this.sequences.add(child);
			return this;
		}

		/**
		 * Append the given sequence as an adjacent sibling of the last appended
		 * sequence.
		 * 
		 * @param sibling
		 * @return
		 */
		public Selector adjacentFollowingSibling(final Sequence sibling) {
			// + separated: means 2 adjacent children
			this.combinators.add(Combinator.ADJACENT_SIBLING);
			this.sequences.add(sibling);
			return this;
		}

		/**
		 * Append the given sequence as a general sibling of the last appended
		 * sequence.
		 * 
		 * @param sibling
		 * @return
		 */
		public Selector generalFollowingSibling(final Sequence sibling) {
			// - separated: means 2 children but not necessarily adjacents
			this.combinators.add(Combinator.GENERAL_SIBLING);
			this.sequences.add(sibling);
			return this;
		}

		/**
		 * Set the pseudo element this selector represents;
		 * 
		 * @param pseudoElement
		 *            the pseudoElement to set
		 */
		public void setPseudoElement(final PseudoElement pseudoElement) {
			this.pseudoElement = pseudoElement;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String s = "";
			for (int i = 0; i < sequences.size(); i++) {
				Sequence sequence = sequences.get(i);
				Combinator combinator = combinators.get(i);
				s += sequence.toString() + combinator.toString();

			}
			return super.toString();
		}

	}

	/**
	 * Base class for any selector component.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class SelectorBase {

		private final String value;

		/**
		 * Construct a {@link SelectorBase} with the given value.
		 */
		public SelectorBase(final String value) {
			this.value = value;
		}

		/**
		 * @return the value of the selector
		 */
		public String get() {
			return value;
		}

		/**
		 * Return the value as string.
		 * 
		 * @return the value
		 */
		@Override
		public String toString() {
			return value;
		}

	}

	/**
	 * Marker interface for a selector that can be at first in a sequence of
	 * simple selectors.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static interface FirstSequenceSelector {

	}

	/**
	 * A sequence of simple selectors.
	 * 
	 * @see <a href="http://www.w3.org/TR/selectors/#sequence">sequence of
	 *      simple selectors in W3C specs</a>
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class Sequence {

		private FirstSequenceSelector firstSelector;
		private final List<SimpleSelector> selectors = new ArrayList<SimpleSelector>();

		public Sequence() {
			super();
			firstSelector = new Universal(Universal.inDefaultNamespace());
		}

		public Sequence(final FirstSequenceSelector selector) {
			super();
			firstSelector = selector;
		}

		public Sequence set(final FirstSequenceSelector selector) {
			firstSelector = selector;
			return this;
		}

		public Sequence add(final SimpleSelector selector) {
			selectors.add(selector);
			return this;
		}

		/**
		 * Set the type selector used in this sequence. If the given selector is
		 * null, this sequence represents the universal selector.
		 * <p>
		 * Calling this method will clear any effect implied by a previous call
		 * to {@link #setUniversal(Universal)}.
		 * 
		 * @param firstSelector
		 * @return this sequence for chaining
		 */
		public Sequence setType(final Type typeSelector) {
			return set(typeSelector);
		}

		/**
		 * 
		 * @param universalSelector
		 * @return
		 */
		public Sequence setUniversal(final Universal universalSelector) {
			return set(universalSelector);
		}

		/**
		 * Append the given selector to this sequence
		 * 
		 * @param selector
		 * @return this sequence for chaining
		 */
		public Sequence append(final Class selector) {
			selectors.add(selector);
			return this;
		}

		/**
		 * Append the given selector to this sequence
		 * 
		 * @param selector
		 * @return this sequence for chaining
		 */
		public Sequence append(final Attribute selector) {
			selectors.add(selector);
			return this;
		}

		/**
		 * Append the given selector to this sequence
		 * 
		 * @param selector
		 * @return this sequence for chaining
		 */
		public Sequence append(final ID selector) {
			selectors.add(selector);
			return this;
		}

		/**
		 * Append the given selector to this sequence
		 * 
		 * @param selector
		 * @return this sequence for chaining
		 */
		public Sequence append(final PseudoClass selector) {
			selectors.add(selector);
			return this;
		}

		/**
		 * Remove the last selector added.
		 * 
		 * @return this sequence for chaining
		 */
		public Sequence pop() {
			if (selectors.size() > 0) {
				selectors.remove(selectors.size() - 1);
			}
			return this;
		}

	}

	public static class SimpleSelector extends SelectorBase {

		public SimpleSelector(final String value) {
			super(value);
		}

	}

	public static class QualifiedName {
		private final String namespace;
		private final String name;

		public static final String NAMESPACE_SEPARATOR = "|";

		private QualifiedName(final String namespace, final String name) {
			super();
			this.namespace = namespace;
			this.name = name;
		}

		public static QualifiedName inNamespace(final String namespace, final String name) {
			return new QualifiedName(namespace, name);
		}

		public static QualifiedName inAnyNamespace(final String name) {
			return new QualifiedName(Selectors.ANY, name);
		}

		public static QualifiedName withoutNamespace(final String name) {
			return new QualifiedName(Selectors.EMPTY, name);
		}

		public static QualifiedName inDefaultNamespace(final String name) {
			return new QualifiedName(null, name);
		}

		/**
		 * Return the namespace and name separated by
		 * {@link #NAMESPACE_SEPARATOR}. If <code>namespace</code> is null,
		 * return only the <code>name</code>.
		 * 
		 * @return the namespace and name separated by
		 *         {@link #NAMESPACE_SEPARATOR}, or only <code>name</code> if
		 *         <code>namespace</code> is null.
		 */
		public String get() {
			if (namespace == null) {
				return name;
			} else {
				return namespace + QualifiedName.NAMESPACE_SEPARATOR + name;
			}
		}

		/**
		 * Same as {@link #get()}.
		 */
		@Override
		public String toString() {
			return get();
		}
	}

	/**
	 * Defines methods for a Universal selector, i.e a selector which match any
	 * element.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class Universal extends SimpleSelector implements FirstSequenceSelector {

		private Universal(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching all the elements in the default namespace
		 * if defined. If the default namespace is not defined, the returned
		 * selector apply on the same elements as the selector returned by
		 * {@link #inAnyNamespace()}.
		 * 
		 * @return the selector
		 */
		public static String inDefaultNamespace() {
			return QualifiedName.inDefaultNamespace(Selectors.ANY).get();
		}

		/**
		 * Return a selector matching all the elements in the given namespace.
		 * <p>
		 * 
		 * @param namespace
		 *            the namespace to select; if null, returns the same
		 *            selector as {@link #all()}.
		 * @return the selector
		 */
		public static String inNamespace(final String namespace) {
			return QualifiedName.inNamespace(namespace, Selectors.ANY).get();
		}

		/**
		 * Return a selector matching all elements, with or whithout any
		 * namespace.
		 * 
		 * @return the selector
		 */
		public static String inAnyNamespace() {
			return QualifiedName.inAnyNamespace(Selectors.ANY).get();
		}

		/**
		 * Return a selector matching all elements without a namespace.
		 * <p>
		 * Elements with a namespace are not included.
		 * 
		 * @return the selector
		 */
		public static String withoutNamespace() {
			return QualifiedName.withoutNamespace(Selectors.ANY).get();
		}

		// =========

		/**
		 * Return a selector matching all the elements in the default namespace
		 * if defined. If the default namespace is not defined, the returned
		 * selector apply on the same elements as the selector returned by
		 * {@link #inAnyNamespace()}.
		 * 
		 * @return the selector
		 */
		public static Universal inDefaultNamespaceUniversal() {
			return new Universal(QualifiedName.inDefaultNamespace(Selectors.ANY).get());
		}

		/**
		 * Return a selector matching all the elements in the given namespace.
		 * <p>
		 * 
		 * @param namespace
		 *            the namespace to select; if null, returns the same
		 *            selector as {@link #all()}.
		 * @return the selector
		 */
		public static Universal inNamespaceUniversal(final String namespace) {
			return new Universal(QualifiedName.inNamespace(namespace, Selectors.ANY).get());
		}

		/**
		 * Return a selector matching all elements, with or whithout any
		 * namespace.
		 * 
		 * @return the selector
		 */
		public static Universal inAnyNamespaceUniversal() {
			return new Universal(QualifiedName.inAnyNamespace(Selectors.ANY).get());
		}

		/**
		 * Return a selector matching all elements without a namespace.
		 * <p>
		 * Elements with a namespace are not included.
		 * 
		 * @return the selector
		 */
		public static Universal withoutNamespaceUniversal() {
			return new Universal(QualifiedName.withoutNamespace(Selectors.ANY).get());
		}
	}

	public static class Type extends SimpleSelector implements FirstSequenceSelector {

		private Type(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching elements with the given tag name in the
		 * default namespace, if the default namespace has been defined. If not
		 * defined, the selector is the same as {@link #inAnyNamespace(String)}.
		 * 
		 * @param elementTagName
		 * @return the selector
		 */
		public static String inDefaultNamespace(final String elementTagName) {
			return QualifiedName.inDefaultNamespace(elementTagName).get();
		}

		/**
		 * Return a selector matching elements with the given tag name in the
		 * given namespace.
		 * 
		 * @param namespace
		 * @param elementTagName
		 * @return
		 */
		public static String inNamespace(final String namespace, final String elementTagName) {
			return QualifiedName.inNamespace(namespace, elementTagName).get();
		}

		/**
		 * Return a selector matching elements with the given tag name and
		 * having no namespace defined.
		 * 
		 * @param namespace
		 * @param elementTagName
		 * @return
		 */
		public static String withoutNamespace(final String elementTagName) {
			return QualifiedName.withoutNamespace(elementTagName).get();
		}

		/**
		 * Return a selector matching elements with the given tag name, with or
		 * without any namespace.
		 * 
		 * @param elementTagName
		 * @return
		 */
		public static String inAnyNamespace(final String elementTagName) {
			return QualifiedName.inAnyNamespace(elementTagName).get();
		}

		// ===========

		/**
		 * Return a selector matching elements with the given tag name in the
		 * default namespace, if the default namespace has been defined. If not
		 * defined, the selector is the same as {@link #inAnyNamespace(String)}.
		 * 
		 * @param elementTagName
		 * @return the selector
		 */
		public static Type inDefaultNamespaceType(final String elementTagName) {
			return new Type(QualifiedName.inDefaultNamespace(elementTagName).get());
		}

		/**
		 * Return a selector matching elements with the given tag name in the
		 * given namespace.
		 * 
		 * @param namespace
		 * @param elementTagName
		 * @return
		 */
		public static Type inNamespaceType(final String namespace, final String elementTagName) {
			return new Type(QualifiedName.inNamespace(namespace, elementTagName).get());
		}

		public static Type inNamespaceType(final QualifiedName name) {
			return new Type(name.get());
		}

		/**
		 * Return a selector matching elements with the given tag name and
		 * having no namespace defined.
		 * 
		 * @param namespace
		 * @param elementTagName
		 * @return
		 */
		public static Type withoutNamespaceType(final String elementTagName) {
			return new Type(QualifiedName.withoutNamespace(elementTagName).get());
		}

		/**
		 * Return a selector matching elements with the given tag name, with or
		 * without any namespace.
		 * 
		 * @param elementTagName
		 * @return
		 */
		public static Type inAnyNamespaceType(final String elementTagName) {
			return new Type(QualifiedName.inAnyNamespace(elementTagName).get());
		}
	}

	public static class Attribute extends SimpleSelector {

		private Attribute(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name specified, whatever its value is.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @return the selector
		 */
		public static String has(final String attributeName) {
			return "[" + attributeName + "]";
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name specified, whatever its value is.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @return the selector
		 */
		public static String has(final QualifiedName attributeName) {
			return "[" + attributeName + "]";
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name whose value is exactly equals to the provided value.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @param value
		 *            the value to match
		 * @return the selector
		 */
		public static String valueEquals(final String attributeName, final String value) {
			return "[" + attributeName + "=\"" + value + "\"]";
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name whose value is exactly equals to the provided value.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @param value
		 *            the value to match
		 * @return the selector
		 */
		public static String valueEquals(final QualifiedName attributeName, final String value) {
			return "[" + attributeName + "=\"" + value + "\"]";
		}

		/**
		 * Return a selector matching elements having an attribute's value
		 * containing a chain of whitespace-separated values whose one of which
		 * exactly equals to the provided value.
		 * 
		 * @param attributeName
		 * @param value
		 * @return the selector
		 */
		public static String hasValueEquals(final String attributeName, final String value) {
			return "[" + attributeName + "~=\"" + value + "\"]";
		}

		/**
		 * Return a selector matching elements having an attribute's value
		 * containing a chain of whitespace-separated values whose one of which
		 * exactly equals to the provided value.
		 * 
		 * @param attributeName
		 * @param value
		 * @return the selector
		 */
		public static String hasValueEquals(final QualifiedName attributeName, final String value) {
			return "[" + attributeName + "~=\"" + value + "\"]";
		}

		/**
		 * Return a selector matching elements whose <code>class</code>
		 * attribute contains the given class name.
		 * 
		 * @param className
		 *            the class name
		 * @return the selector
		 */
		public static String hasClass(final String className) {
			return hasValueEquals("class", className);
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param prefix
		 *            the prefix
		 * @return the selector
		 */
		public static String startsWith(final String attributeName, final String prefix) {
			return "[" + attributeName + "^=\"" + prefix + "\"]";
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param suffix
		 *            the suffix
		 * @return the selector
		 */
		public static String endsWith(final String attributeName, final String suffix) {
			return "[" + attributeName + "$=\"" + suffix + "\"]";
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> contains the given substring.
		 * 
		 * @param substring
		 *            the substring
		 * @return the selector
		 */
		public static String contains(final String attributeName, final String substring) {
			return "[" + attributeName + "*=\"" + substring + "\"]";
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> contains a hyphen-separated chain of
		 * values begining (from the left) with the given prefix.
		 * 
		 * @param prefix
		 *            the prefix
		 * @return the selector
		 */
		public static String hyphenSeparatedValueStartsWith(final String attributeName, final String prefix) {
			return "[" + attributeName + "|=\"" + prefix + "\"]";
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name specified, whatever its value is.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @return the selector
		 */
		public static Attribute hasAttribute(final String attributeName) {
			return new Attribute("[" + attributeName + "]");
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name specified, whatever its value is.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @return the selector
		 */
		public static Attribute hasAttribute(final QualifiedName attributeName) {
			return new Attribute("[" + attributeName + "]");
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name whose value is exactly equals to the provided value.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @param value
		 *            the value to match
		 * @return the selector
		 */
		public static Attribute valueEqualsAttribute(final String attributeName, final String value) {
			return new Attribute("[" + attributeName + "=\"" + value + "\"]");
		}

		/**
		 * Return a selector matching elements having the attribute with the
		 * given name whose value is exactly equals to the provided value.
		 * 
		 * @param attributeName
		 *            the name of the attribute
		 * @param value
		 *            the value to match
		 * @return the selector
		 */
		public static Attribute valueEqualsAttribute(final QualifiedName attributeName, final String value) {
			return new Attribute("[" + attributeName + "=\"" + value + "\"]");
		}

		/**
		 * Return a selector matching elements having an attribute's value
		 * containing a chain of whitespace-separated values whose one of which
		 * exactly equals to the provided value.
		 * 
		 * @param attributeName
		 * @param value
		 * @return the selector
		 */
		public static Attribute hasValueEqualsAttribute(final String attributeName, final String value) {
			return new Attribute("[" + attributeName + "~=\"" + value + "\"]");
		}

		/**
		 * Return a selector matching elements having an attribute's value
		 * containing a chain of whitespace-separated values whose one of which
		 * exactly equals to the provided value.
		 * 
		 * @param attributeName
		 * @param value
		 * @return the selector
		 */
		public static Attribute hasValueEqualsAttribute(final QualifiedName attributeName, final String value) {
			return new Attribute("[" + attributeName + "~=\"" + value + "\"]");
		}

		/**
		 * Return a selector matching elements whose <code>class</code>
		 * attribute contains the given class name.
		 * 
		 * @param className
		 *            the class name
		 * @return the selector
		 */
		public static Attribute hasClassAttribute(final String className) {
			return new Attribute(hasValueEquals("class", className));
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param prefix
		 *            the prefix
		 * @return the selector
		 */
		public static Attribute startsWithAttribute(final String attributeName, final String prefix) {
			return new Attribute("[" + attributeName + "^=\"" + prefix + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param suffix
		 *            the suffix
		 * @return the selector
		 */
		public static Attribute endsWithAttribute(final String attributeName, final String suffix) {
			return new Attribute("[" + attributeName + "$=\"" + suffix + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> contains the given substring.
		 * 
		 * @param substring
		 *            the substring
		 * @return the selector
		 */
		public static Attribute containsAttribute(final String attributeName, final String substring) {
			return new Attribute("[" + attributeName + "*=\"" + substring + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param prefix
		 *            the prefix
		 * @return the selector
		 */
		public static Attribute startsWithAttribute(final QualifiedName attributeName, final String prefix) {
			return new Attribute("[" + attributeName + "^=\"" + prefix + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> starts with the given prefix.
		 * 
		 * @param suffix
		 *            the suffix
		 * @return the selector
		 */
		public static Attribute endsWithAttribute(final QualifiedName attributeName, final String suffix) {
			return new Attribute("[" + attributeName + "$=\"" + suffix + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> contains the given substring.
		 * 
		 * @param substring
		 *            the substring
		 * @return the selector
		 */
		public static Attribute containsAttribute(final QualifiedName attributeName, final String substring) {
			return new Attribute("[" + attributeName + "*=\"" + substring + "\"]");
		}

		/**
		 * Return a selector matching elements whose the attribute named
		 * <code>attributeName</code> contains a hyphen-separated chain of
		 * values begining (from the left) with the given prefix.
		 * 
		 * @param prefix
		 *            the prefix
		 * @return the selector
		 */
		public static Attribute hyphenSeparatedValueStartsWithAttribute(final String attributeName, final String prefix) {
			return new Attribute("[" + attributeName + "|=\"" + prefix + "\"]");
		}
	}

	/**
	 * {@link SimpleSelector} matching elements according to their class
	 * attribute.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class Class extends SimpleSelector {

		private Class(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching elements whose class attribute contains a
		 * whitespace-separated chain of values, one of which is as
		 * 
		 * @param className
		 *            the name of the class
		 * @return the selector
		 */
		public static String has(final String className) {
			return "." + className;
		}

		/**
		 * Return a selector matching elements whose class attribute contains a
		 * whitespace-separated chain of values, one of which is as
		 * 
		 * @param className
		 *            the name of the class
		 * @return the selector
		 */
		public static Class hasClass(final String className) {
			return new Class(has(className));
		}
	}

	/**
	 * {@link SimpleSelector} matching elements based on their id attribute
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class ID extends SimpleSelector {

		private ID(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching elements whose <code>id</code> attribute
		 * is equals to the given value.
		 * 
		 * @param id
		 * @return the selector
		 */
		public static String has(final String id) {
			return "#" + id;
		}

		/**
		 * Return a selector matching elements whose <code>id</code> attribute
		 * is equals to the given value.
		 * 
		 * @param id
		 * @return the selector
		 */
		public static ID hasID(final String id) {
			return new ID(has(id));
		}
	}

	public static class PseudoClass extends SimpleSelector {

		private PseudoClass(final String value) {
			super(value);
		}

		/**
		 * Return a selector matching elements corresponding to the specified
		 * pseudo class .
		 * 
		 * @param id
		 * @return the selector
		 */
		protected static String create(final String pseudoClass) {
			return ":" + pseudoClass;
		}

		/**
		 * Return a selector matching links that have never been visited.
		 * 
		 * @return the selector
		 */
		public static String link() {
			return create("link");
		}

		/**
		 * Return a selector matching links that have been visited at least once
		 * by the user.
		 * 
		 * @return the selector
		 */
		public static String visited() {
			return create("visited");
		}

		/**
		 * Return a selector matching elements designated by the user with a
		 * pointing device, but does not necessarily activate it. For example, a
		 * visual user agent could apply this pseudo-class when the cursor
		 * (mouse pointer) hovers over a box generated by the element.
		 * 
		 * @return the selector
		 */
		public static String hover() {
			return create("hover");
		}

		/**
		 * Return a selector matching elements being activated by the user. For
		 * example, between the times the user presses the mouse button and
		 * releases it. On systems with more than one mouse button, :active
		 * applies only to the primary or primary activation button (typically
		 * the "left" mouse button), and any aliases thereof.
		 * 
		 * @return the selector
		 */
		public static String active() {
			return create("active");
		}

		/**
		 * Return a selector matching elements having the focus (accepts
		 * keyboard or mouse events, or other forms of input).
		 * 
		 * @return the selector
		 */
		public static String focus() {
			return create("focus");
		}

		/**
		 * Return a selector matching elements which are the targets (i.e the
		 * fragment identifier) in the URI of the document.
		 * 
		 * @return the selector
		 */
		public static String target() {
			return create("target");
		}

		/**
		 * Return a selector matching elements which are enabled.
		 * 
		 * @return the selector
		 */
		public static String enabled() {
			return create("enabled");
		}

		/**
		 * Return a selector matching elements which are disabled.
		 * 
		 * @return the selector
		 */
		public static String disabled() {
			return create("disabled");
		}

		/**
		 * Return a selector matching elements which are checked or selected.
		 * 
		 * @return the selector
		 */
		public static String checked() {
			return create("checked");
		}

		/**
		 * Return a selector matching elements which are neither checked nor
		 * unchecked.
		 * 
		 * @return the selector
		 */
		public static String indeterminate() {
			return create("indeterminate");
		}

		/**
		 * Return a selector matching elements based on the language defined by
		 * the document.
		 * <p>
		 * 
		 * 
		 * @param langOrPrefix
		 *            a full language designation ("fr-fr") or a language prefix
		 *            ("de").
		 * @return the selector
		 */
		public static String lang(final String langOrPrefix) {
			return create("lang") + "(" + langOrPrefix + ")";
		}

		/**
		 * Return a selector of the <code>:root</code> pseudo-class, represents
		 * an element that is the root of the document.
		 * <p>
		 * In HTML 4, this is always the HTML element.
		 * 
		 * @return the ":root" pseudo-class
		 */
		public static String root() {
			return create("root");
		}

		/**
		 * Return a selector matching the first child of an element.
		 * 
		 * @return the selector
		 */
		public static String firstChild() {
			return create("first-child");
		}

		/**
		 * Return a selector matching the first element of the given type.
		 * 
		 * @return the selector
		 */
		public static String firstOfType() {
			return create("first-of-type");
		}

		/**
		 * Return a selector matching the last element of the given type.
		 * 
		 * @return the selector
		 */
		public static String lastOfType() {
			return create("last-of-type");
		}

		/**
		 * Return a selector matching an element if it is the only child of its
		 * parent.
		 * 
		 * @return the selector
		 */
		public static String onlyChild() {
			return create("only-child");
		}

		/**
		 * Return a selector matching an element if it is the only element of
		 * this type among the children of its parent.
		 * 
		 * @return the selector
		 */
		public static String onlyOfType() {
			return create("only-of-type");
		}

		/**
		 * Return a selector matching an element if it has no child (even text
		 * nodes and so on).
		 * 
		 * @return the selector
		 */
		public static String empty() {
			return create("empty");
		}

		/**
		 * Return a selector matching the last child of an element.
		 * 
		 * @return the selector
		 */
		public static String lastChild() {
			return create("last-child");
		}

		/**
		 * Return a selector matching the n-th child of an element. This first
		 * child has index 1.
		 * 
		 * @param index
		 *            the n-th index
		 * @return the selector
		 */
		public static String child(final int index) {
			if (index < 1) {
				throw new IllegalArgumentException("index must be positive or zero");
			}
			return create("nth-child(" + index + ")");
		}

		/**
		 * Return a selector matching children an element that have
		 * <code>a<i>n</i>+b-1</code> siblings before it in the document tree,
		 * for any positive integer or zero value of <i>n</i> .
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static String child(final int a, final int b) {
			return create("nth-child(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")");
		}

		/**
		 * Return a selector matching children elements that has
		 * <code>a<i>n</i>+b-1</code> siblings after it in the document tree,
		 * for any positive integer or zero value of <i>n</i> .
		 * 
		 * @see <a
		 *      href="http://www.w3.org/TR/selectors/#nth-last-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static String lastChild(final int a, final int b) {
			return create("nth-last-child(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")");
		}

		/**
		 * Return a selector matching elements that has an+b-1 siblings of the
		 * same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static String ofType(final int a, final int b) {
			return create("nth-of-type(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")");
		}

		/**
		 * Return a selector matching odd sibling elements of the same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static String ofTypeOdd() {
			return create("nth-of-type(odd)");
		}

		/**
		 * Return a selector matching even sibling elements of the same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static String ofTypeEven() {
			return create("nth-of-type(even)");
		}

		/**
		 * Return a selector matching odd children of a parent element.
		 * 
		 * @return the selector
		 */
		public static String childOdd() {
			return create("nth-child(odd)");
		}

		/**
		 * Return a selector matching even children of a parent element.
		 * 
		 * @return the selector
		 */
		public static String childEven() {
			return create("nth-child(even)");
		}

		public static String not(final SimpleSelector selector) {
			if (selector.get().startsWith(":not")) {
				throw new IllegalArgumentException("cannot negate a :not selector");
			}
			return create("not") + "(" + selector.get() + ")";
		}

		/**
		 * Return a selector matching links that have never been visited.
		 * 
		 * @return the selector
		 */
		public static PseudoClass linkPseudoClass() {
			return new PseudoClass(create("link"));
		}

		/**
		 * Return a selector matching links that have been visited at least once
		 * by the user.
		 * 
		 * @return the selector
		 */
		public static PseudoClass visitedPseudoClass() {
			return new PseudoClass(create("visited"));
		}

		/**
		 * Return a selector matching elements designated by the user with a
		 * pointing device, but does not necessarily activate it. For example, a
		 * visual user agent could apply this pseudo-class when the cursor
		 * (mouse pointer) hovers over a box generated by the element.
		 * 
		 * @return the selector
		 */
		public static PseudoClass hoverPseudoClass() {
			return new PseudoClass(create("hover"));
		}

		/**
		 * Return a selector matching elements being activated by the user. For
		 * example, between the times the user presses the mouse button and
		 * releases it. On systems with more than one mouse button, :active
		 * applies only to the primary or primary activation button (typically
		 * the "left" mouse button), and any aliases thereof.
		 * 
		 * @return the selector
		 */
		public static PseudoClass activePseudoClass() {
			return new PseudoClass(create("active"));
		}

		/**
		 * Return a selector matching elements having the focus (accepts
		 * keyboard or mouse events, or other forms of input).
		 * 
		 * @return the selector
		 */
		public static PseudoClass focusPseudoClass() {
			return new PseudoClass(create("focus"));
		}

		/**
		 * Return a selector matching elements which are the targets (i.e the
		 * fragment identifier) in the URI of the document.
		 * 
		 * @return the selector
		 */
		public static PseudoClass targetPseudoClass() {
			return new PseudoClass(create("target"));
		}

		/**
		 * Return a selector matching elements which are enabled.
		 * 
		 * @return the selector
		 */
		public static PseudoClass enabledPseudoClass() {
			return new PseudoClass(create("enabled"));
		}

		/**
		 * Return a selector matching elements which are disabled.
		 * 
		 * @return the selector
		 */
		public static PseudoClass disabledPseudoClass() {
			return new PseudoClass(create("disabled"));
		}

		/**
		 * Return a selector matching elements which are checked or selected.
		 * 
		 * @return the selector
		 */
		public static PseudoClass checkedPseudoClass() {
			return new PseudoClass(create("checked"));
		}

		/**
		 * Return a selector matching elements which are neither checked nor
		 * unchecked.
		 * 
		 * @return the selector
		 */
		public static PseudoClass indeterminatePseudoClass() {
			return new PseudoClass(create("indeterminate"));
		}

		/**
		 * Return a selector matching elements based on the language defined by
		 * the document.
		 * <p>
		 * 
		 * 
		 * @param langOrPrefix
		 *            a full language designation ("fr-fr") or a language prefix
		 *            ("de").
		 * @return the selector
		 */
		public static PseudoClass langPseudoClass(final String langOrPrefix) {
			return new PseudoClass(create("lang") + "(" + langOrPrefix + ")");
		}

		/**
		 * Return a selector of the <code>:root</code> pseudo-class, represents
		 * an element that is the root of the document.
		 * <p>
		 * In HTML 4, this is always the HTML element.
		 * 
		 * @return the ":root" pseudo-class
		 */
		public static PseudoClass rootPseudoClass() {
			return new PseudoClass(create("root"));
		}

		/**
		 * Return a selector matching the first child of an element.
		 * 
		 * @return the selector
		 */
		public static PseudoClass firstChildPseudoClass() {
			return new PseudoClass(create("first-child"));
		}

		/**
		 * Return a selector matching the first element of the given type.
		 * 
		 * @return the selector
		 */
		public static PseudoClass firstOfTypePseudoClass() {
			return new PseudoClass(create("first-of-type"));
		}

		/**
		 * Return a selector matching the last element of the given type.
		 * 
		 * @return the selector
		 */
		public static PseudoClass lastOfTypePseudoClass() {
			return new PseudoClass(create("last-of-type"));
		}

		/**
		 * Return a selector matching an element if it is the only child of its
		 * parent.
		 * 
		 * @return the selector
		 */
		public static PseudoClass onlyChildPseudoClass() {
			return new PseudoClass(create("only-child"));
		}

		/**
		 * Return a selector matching an element if it is the only element of
		 * this type among the children of its parent.
		 * 
		 * @return the selector
		 */
		public static PseudoClass onlyOfTypePseudoClass() {
			return new PseudoClass(create("only-of-type"));
		}

		/**
		 * Return a selector matching an element if it has no child (even text
		 * nodes and so on).
		 * 
		 * @return the selector
		 */
		public static PseudoClass emptyPseudoClass() {
			return new PseudoClass(create("empty"));
		}

		/**
		 * Return a selector matching the last child of an element.
		 * 
		 * @return the selector
		 */
		public static PseudoClass lastChildPseudoClass() {
			return new PseudoClass(create("last-child"));
		}

		/**
		 * Return a selector matching the n-th child of an element. This first
		 * child has index 1.
		 * 
		 * @param index
		 *            the n-th index
		 * @return the selector
		 */
		public static PseudoClass childPseudoClass(final int index) {
			if (index < 1) {
				throw new IllegalArgumentException("index must be positive or zero");
			}
			return new PseudoClass(create("nth-child(" + index + ")"));
		}

		/**
		 * Return a selector matching children an element that have
		 * <code>a<i>n</i>+b-1</code> siblings before it in the document tree,
		 * for any positive integer or zero value of <i>n</i> .
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static PseudoClass childPseudoClass(final int a, final int b) {
			return new PseudoClass(create("nth-child(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")"));
		}

		/**
		 * Return a selector matching children elements that has
		 * <code>a<i>n</i>+b-1</code> siblings after it in the document tree,
		 * for any positive integer or zero value of <i>n</i> .
		 * 
		 * @see <a
		 *      href="http://www.w3.org/TR/selectors/#nth-last-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static PseudoClass lastChildPseudoClass(final int a, final int b) {
			return new PseudoClass(create("nth-last-child(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")"));
		}

		/**
		 * Return a selector matching elements that has an+b-1 siblings of the
		 * same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static PseudoClass ofTypePseudoClass(final int a, final int b) {
			return new PseudoClass(create("nth-of-type(" + a + "n" + (b >= 0 ? '+' : '-') + b + ")"));
		}

		/**
		 * Return a selector matching odd sibling elements of the same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static PseudoClass ofTypeOddPseudoClass() {
			return new PseudoClass(create("nth-of-type(odd)"));
		}

		/**
		 * Return a selector matching even sibling elements of the same type.
		 * 
		 * @see <a href="http://www.w3.org/TR/selectors/#nth-child-pseudo">w3c
		 *      specs</a>
		 * @param a
		 *            a positive, negative or zero integer
		 * @param b
		 *            a positive, negative or zero integer
		 * @return the selector
		 */
		public static PseudoClass ofTypeEvenPseudoClass() {
			return new PseudoClass(create("nth-of-type(even)"));
		}

		/**
		 * Return a selector matching odd children of a parent element.
		 * 
		 * @return the selector
		 */
		public static PseudoClass childOddPseudoClass() {
			return new PseudoClass(create("nth-child(odd)"));
		}

		/**
		 * Return a selector matching even children of a parent element.
		 * 
		 * @return the selector
		 */
		public static PseudoClass childEvenPseudoClass() {
			return new PseudoClass(create("nth-child(even)"));
		}

		public static PseudoClass notPseudoClass(final SimpleSelector selector) {
			if (selector.get().startsWith(":not")) {
				throw new IllegalArgumentException("cannot negate a :not selector");
			}
			return new PseudoClass(create("not") + "(" + selector.get() + ")");
		}

	}

	/**
	 * Pseudo elements can be located only once by {@link Selector}.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class PseudoElement extends SelectorBase {

		private PseudoElement(final String value) {
			super(value);
		}

		protected static String create(final String pseudoElement) {
			return "::" + pseudoElement;
		}

		/**
		 * Return a selector matching the first line of a text.
		 * 
		 * @return the selector
		 */
		public static String firstLine() {
			return create("first-line");
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static String firstLetter() {
			return create("first-letter");
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static String before() {
			return create("before");
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static String after() {
			return create("after");
		}

		/**
		 * Return a selector matching the first line of a text.
		 * 
		 * @return the selector
		 */
		public static PseudoElement firstLinePseudoElement() {
			return new PseudoElement(create("first-line"));
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static PseudoElement firstLetterPseudoElement() {
			return new PseudoElement(create("first-letter"));
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static PseudoElement beforePseudoElement() {
			return new PseudoElement(create("before"));
		}

		/**
		 * Return a selector matching the first letter of a text.
		 * 
		 * @return the selector
		 */
		public static PseudoElement afterPseudoElement() {
			return new PseudoElement(create("after"));
		}

	}

}
