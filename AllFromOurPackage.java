/*
 * Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the Classpath exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.btrace.samples;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

/**
 * This script displays method call tree on petclinic project:
 * https://github.com/spring-projects/spring-petclinic
 */

@BTrace
public class AllFromOurPackage {
    private static final String ORG_SPRINGFRAMEWORK_SAMPLES_PETCLINIC = "/org.springframework.samples.petclinic.*/";
    private static volatile String idention = "";

    @OnMethod(clazz = ORG_SPRINGFRAMEWORK_SAMPLES_PETCLINIC, method = "/.*/",
            location = @Location(value = Kind.ENTRY))
    public static void m(@Self Object self, @ProbeMethodName String probeMethod, AnyType[] args) { // all calls to the methods with signature "()"
        print(idention + classOf(self) + "." + probeMethod + "\n" );
        idention = Strings.concat(idention, "\t");
    }

    @OnMethod(clazz = ORG_SPRINGFRAMEWORK_SAMPLES_PETCLINIC, method = "/.*/", location = @Location(value = Kind.RETURN, clazz = "/.*/", method = "/.*/"))
    public static void mr() {

        if(Strings.length(idention) > 1 )
        idention = Strings.substr(idention, 0, Strings.length(idention)-1);
    }
}
