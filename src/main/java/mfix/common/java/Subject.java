/**
 * Copyright (C) SEI, PKU, PRC. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is
 * strictly prohibited Proprietary and Confidential.
 * Written by Jiajun Jiang<jiajun.jiang@pku.edu.cn>.
 */

package mfix.common.java;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jiajun
 * @date: 9/21/18
 */
public class Subject {

    protected String _base = null;
    protected String _name = null;
    protected String _ssrc = null;
    protected String _tsrc = null;
    protected String _sbin = null;
    protected String _tbin = null;
    protected String _src_level = null;
    protected List<String> _classpath;

    protected Subject(String base, String name) {
        this(base, name, null, null, null, null);
    }
    /**
     * subject
     *
     * @param base : the base directory of subject, e.g., "/home/ubuntu/code/chart"
     * @param name : name of subject, e.g., "chart".
     * @param ssrc : relative path for source folder, e.g., "/source"
     * @param tsrc : relative path for test folder, e.g., "/tests"
     * @param sbin : relative path for source byte code, e.g., "/classes"
     * @param tbin : relative path for test byte code, e.g., "/test-classes"
     */
    public Subject(String base, String name, String ssrc, String tsrc, String sbin, String tbin) {
        this(base, name, ssrc, tsrc, sbin, tbin, "1.7", new LinkedList<String>());
    }

    public Subject(String base, String name, String ssrc, String tsrc, String sbin, String tbin, String sourceLevel, List<String> classpath) {
        _name = name;
        _ssrc = ssrc;
        _tsrc = tsrc;
        _sbin = sbin;
        _tbin = tbin;
        _src_level = sourceLevel;
        _classpath = classpath;
    }

    public String getBasePath() {
        return _base;
    }

    public String getName() {
        return _name;
    }

    public String getSsrc() {
        return _ssrc;
    }

    public String getTsrc() {
        return _tsrc;
    }

    public String getSbin() {
        return _sbin;
    }

    public String getTbin() {
        return _tbin;
    }

    public String getSourceLevel() {
        return _src_level;
    }

    public void setClasspath(List<String> classpath) {
        _classpath = classpath;
    }

    public List<String> getClasspath() {
        return _classpath;
    }

    public boolean checkAndInitBuildDir() {
        File file = new File(getBasePath() + getSbin());
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(getBasePath() + getTbin());
        if (!file.exists()) {
            file.mkdirs();
        }
        return true;
    }

    @Override
    public String toString() {
        return "[_name=" + _name + ", _ssrc=" + _ssrc + ", _tsrc=" + _tsrc + ", _sbin=" + _sbin
                + ", _tbin=" + _tbin + "]";
    }
}
