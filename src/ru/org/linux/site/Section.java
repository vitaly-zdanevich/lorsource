/*
 * Copyright 1998-2010 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.org.linux.site;

import java.io.Serializable;
import java.sql.*;

public class Section implements Serializable {
  private final String name;
  private final boolean imagepost;
  private final boolean moderate;
  private final int id;
  private final boolean votepoll;
  public static final int SCROLL_NOSCROLL = 0;
  public static final int SCROLL_SECTION = 1;
  public static final int SCROLL_GROUP = 2;

  public static final int SECTION_FORUM = 2;
  public static final int SECTION_GALLERY = 3;

  public Section(Connection db, int id) throws SQLException, SectionNotFoundException {
    this.id = id;

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT name, imagepost, vote, moderate " +
            "FROM sections " +
            "WHERE id="+id
    );

    if (!rs.next()) {
      throw new SectionNotFoundException(id);
    }

    name = rs.getString("name");
    imagepost = rs.getBoolean("imagepost");
    votepoll = rs.getBoolean("vote");
    moderate = rs.getBoolean("moderate");
  }

  public String getName() {
    return name;
  }

  public boolean isImagepost() {
    return imagepost;
  }

  public boolean isVotePoll() {
    return votepoll;
  }

  public static int getScrollMode(int sectionid) {
    switch (sectionid) {
      case 1: /* news*/
      case 3: /* screenshots */
      case 5: /* poll */
        return SCROLL_SECTION;
      case 2: /* forum */
        return SCROLL_GROUP;
      default:
        return SCROLL_NOSCROLL;
    }
  }

  public int getId() {
    return id;
  }

  public boolean isPremoderated() {
    return moderate;
  }

  public String getAddText() {
    if (id==4) {
      return "Добавить ссылку";
    } else {
      return "Добавить сообщение";
    }
  }

  public boolean isForum() {
    return id==2;
  }

  public String getTitle() {
    return name;
  }

  public Timestamp getLastCommitdate(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    try {
      st = db.createStatement();

      rs = st.executeQuery("select max(commitdate) from topics,groups where section=" + id + " and topics.groupid=groups.id");

      if (!rs.next()) {
        return null;
      } else {
        return rs.getTimestamp("max");
      }
    } finally {
      if (rs!=null) {
        rs.close();
      }

      if (st!=null) {
        st.close();
      }
    }
  }

  public String getAddInfo(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    try {
      st = db.createStatement();

      rs = st.executeQuery("select add_info from sections where id=" + id);

      if (!rs.next()) {
        return null;
      } else {
        return rs.getString("add_info");
      }
    } finally {
      if (rs!=null) {
        rs.close();
      }

      if (st!=null) {
        st.close();
      }
    }
  }

  public int getCommentPostscore() {
    //TODO move this to database
    if (id==1 || id==2) {
      return 0;
    } else {
      return 50;
    }
  }

  public static String getSectionLink(int section) {
    if (section==SECTION_FORUM) {
      return "/forum/";
    }

    return "/view-section.jsp?section="+section;
  }

  public String getArchiveLink(int year, int month) {
    if (id==SECTION_GALLERY) {
      return "/gallery/archive/"+year+"/"+month+"/";
    }

    return "/view-news.jsp?section="+id+"&year="+year+"&month="+month;
  }

  public String getArchiveLink() {
    if (id==SECTION_GALLERY) {
      return "/gallery/archive/";
    }

    return "/view-news-archive.jsp?section="+id;
  }

  public Group getGroup(Connection db, String name) throws SQLException, BadGroupException {
    PreparedStatement st = db.prepareStatement("SELECT id FROM groups WHERE section=? AND urlname=?");
    st.setInt(1, id);
    st.setString(2, name);

    ResultSet rs = st.executeQuery();
    if (!rs.next()) {
      throw new BadGroupException("group not found");
    }

    return new Group(db, rs.getInt(1));
  }
}
