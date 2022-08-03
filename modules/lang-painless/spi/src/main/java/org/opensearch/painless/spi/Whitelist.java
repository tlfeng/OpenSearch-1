/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

package org.opensearch.painless.spi;

import org.opensearch.painless.spi.annotation.WhitelistAnnotationParser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Allowlist contains data structures designed to be used to generate an allowlist of Java classes,
 * constructors, methods, and fields that can be used within a Painless script at both compile-time
 * and run-time.
 *
 * An Allowlist consists of several pieces with {@link WhitelistClass}s as the top level. Each
 * {@link WhitelistClass} will contain zero-to-many {@link WhitelistConstructor}s, {@link WhitelistMethod}s, and
 * {@link WhitelistField}s which are what will be available with a Painless script.  See each individual
 * allowlist object for more detail.
 *
 * @deprecated As of 2.2, because supporting inclusive language, replaced by {@link Allowlist}. The class was declared with final keyword.
 */
@Deprecated
public class Whitelist {

    static final String[] BASE_ALLOWLIST_FILES = new String[] {
        "org.opensearch.txt",
        "java.lang.txt",
        "java.math.txt",
        "java.text.txt",
        "java.time.txt",
        "java.time.chrono.txt",
        "java.time.format.txt",
        "java.time.temporal.txt",
        "java.time.zone.txt",
        "java.util.txt",
        "java.util.function.txt",
        "java.util.regex.txt",
        "java.util.stream.txt" };

    public static final List<Whitelist> BASE_WHITELISTS = Collections.singletonList(
        WhitelistLoader.loadFromResourceFiles(Whitelist.class, WhitelistAnnotationParser.BASE_ANNOTATION_PARSERS, BASE_ALLOWLIST_FILES)
    );

    /** The {@link ClassLoader} used to look up the allowlisted Java classes, constructors, methods, and fields. */
    public final ClassLoader classLoader;

    /** The {@link List} of all the allowlisted Painless classes. */
    public final List<WhitelistClass> whitelistClasses;

    /** The {@link List} of all the allowlisted static Painless methods. */
    public final List<WhitelistMethod> whitelistImportedMethods;

    /** The {@link List} of all the allowlisted Painless class bindings. */
    public final List<WhitelistClassBinding> whitelistClassBindings;

    /** The {@link List} of all the allowlisted Painless instance bindings. */
    public final List<WhitelistInstanceBinding> whitelistInstanceBindings;

    /** Standard constructor. All values must be not {@code null}. */
    public Whitelist(
        ClassLoader classLoader,
        List<WhitelistClass> allowlistClasses,
        List<WhitelistMethod> allowlistImportedMethods,
        List<WhitelistClassBinding> allowlistClassBindings,
        List<WhitelistInstanceBinding> allowlistInstanceBindings
    ) {

        this.classLoader = Objects.requireNonNull(classLoader);
        this.whitelistClasses = Collections.unmodifiableList(Objects.requireNonNull(allowlistClasses));
        this.whitelistImportedMethods = Collections.unmodifiableList(Objects.requireNonNull(allowlistImportedMethods));
        this.whitelistClassBindings = Collections.unmodifiableList(Objects.requireNonNull(allowlistClassBindings));
        this.whitelistInstanceBindings = Collections.unmodifiableList(Objects.requireNonNull(allowlistInstanceBindings));
    }
}
