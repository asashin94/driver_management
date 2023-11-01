<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMan" value="${ForwardConst.ACT_MAN.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>運行管理 一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="management_place">配送先</th>
                    <th class="management_driver">ドライバｰ名</th>
                    <th class="management_go">出発時間</th>
                    <th class="management_arrive">到着時間</th>
                    <th class="management_back">戻り時間</th>
                    <th class="management_action">操作</th>
                </tr>
                <c:forEach var="management" items="${managements}" varStatus="status">
                    <fmt:parseDate value="${management.goAt}" pattern="yyyy-MM-dd'T'HH:mm" var="managementGo" type="date" />
                    <fmt:parseDate value="${management.arriveAt}" pattern="yyyy-MM-dd'T'HH:mm" var="managementArrirve" type="date" />
                    <fmt:parseDate value="${management.backAt}" pattern="yyyy-MM-dd'T'HH:mm" var="managementBack" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="management_place"><c:out value="${management.place}" /></td>
                        <td class="management_driver"><c:out value="${management.driver }" /></td>
                        <td class="management_go"><fmt:formatDate value='${managementGo}' pattern='yyyy/MM/dd HH:mm' /></td>
                        <td class="management_arrive"><fmt:formatDate value='${managementArrirve}' pattern='yyyy/MM/dd HH:mm' /></td>
                        <td class="management_back"><fmt:formatDate value='${managementBack}' pattern='yyyy/MM/dd HH:mm' /></td>
                        <td class="management_action"><a href="<c:url value='?action=${actMan}&command=${commShow}&id=${management.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${managements_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((managements_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actMan}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actMan}&command=${commNew}' />">新規運行管理の登録</a></p>
    </c:param>
</c:import>