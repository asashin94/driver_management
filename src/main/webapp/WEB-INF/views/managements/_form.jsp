<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label>入力者</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.DRIVER.getValue()}">ドライバー名</label><br />
<select name="${AttributeConst.DRIVER.getValue()}" id="${AttributeConst.DRIVER.getValue()}" >
<option value="">ドライバーを選択してください</option>
<c:forEach var="driver" items="${drivers}">
        <option value="${driver}">${driver}</option>
    </c:forEach>
</select>
<br /><br />

<label for="${AttributeConst.MAN_PLACE.getValue()}">目的地</label><br />
<input type="text"  name="${AttributeConst.MAN_PLACE.getValue()}" id="${AttributeConst.MAN_PLACE.getValue()}" />
<br /><br />

<fmt:parseDate value="${managemanet.goAt}" pattern="yyyy-MM-dd'T'HH:mm" var="goAt" type="date"/>
<label for="${AttributeConst.MAN_GO.getValue()}">出発時間</label><br />
<input type="date" name="${AttributeConst.MAN_GO.getValue()}" id="${AttributeConst.MAN_GO.getValue()}" value="<fmt:formatDate value='${goAt}' pattern='yyyy-MM-dd\'T\'HH:mm:ss' />" />
<br /><br />

<fmt:parseDate value="${managemanet.arriveAt}" pattern="yyyy-MM-dd'T'HH:mm" var="goAt" type="date"/>
<label for="${AttributeConst.MAN_ARRIVE.getValue()}">到着時間</label><br />
<input type="date" name="${AttributeConst.MAN_ARRIVE.getValue()}" id="${AttributeConst.MAN_ARRIVE.getValue()}" value="<fmt:formatDate value='${arriveAt}' pattern='yyyy-MM-dd\'T\'HH:mm:ss' />" />
<br /><br />

<fmt:parseDate value="${managemanet.backAt}" pattern="yyyy-MM-dd'T'HH:mm" var="goAt" type="date"/>
<label for="${AttributeConst.MAN_BACK.getValue()}">戻り時間</label><br />
<input type="date" name="${AttributeConst.MAN_BACK.getValue()}" id="${AttributeConst.MAN_BACK.getValue()}" value="<fmt:formatDate value='${backAt}' pattern='yyyy-MM-dd\'T\'HH:mm:ss' />" />
<br /><br />

<br /><br />
<input type="hidden" name="${AttributeConst.MAN_ID.getValue()}" value="${management.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>