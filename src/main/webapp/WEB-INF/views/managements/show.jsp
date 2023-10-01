<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMan" value="${ForwardConst.ACT_MAN.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>運行管理 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>入力者</th>
                    <td><c:out value="${management.employee.name}" /></td>
                </tr>
                <tr>
                    <th>ドライバー名</th>
                    <td><c:out value="${management.driver}" /></td>
                </tr>
                <tr>
                    <th>目的地</th>
                    <td><c:out value="${management.place}" /></td>
                </tr>
                <tr>
                    <th>出発時間</th>
                    <fmt:parseDate value="${management.goAt}" pattern="yyyy-MM-dd'T'HH:mm" var="go" type="date" />
                    <td><fmt:formatDate value="${go}" pattern="yyyy-MM-dd HH:mm" /></td>
                </tr>
                <tr>
                    <th>到着時間</th>
                    <fmt:parseDate value="${management.arriveAt}" pattern="yyyy-MM-dd'T'HH:mm" var="arrive" type="date" />
                    <td><fmt:formatDate value="${arrive}" pattern="yyyy-MM-dd HH:mm" /></td>
                </tr>
                <tr>
                    <th>戻り時間</th>
                    <fmt:parseDate value="${management.backAt}" pattern="yyyy-MM-dd'T'HH:mm" var="back" type="date" />
                    <td><fmt:formatDate value="${back}" pattern="yyyy-MM-dd HH:mm" /></td>
                </tr>
            </tbody>
        </table>

        <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この運行管理を編集する</a>


        <p>
            <a href="<c:url value='?action=${actMan}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>