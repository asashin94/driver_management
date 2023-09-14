<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_DRI.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.DRI_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.DRI_NAME.getValue()}" id="${AttributeConst.DRI_NAME.getValue()}" value="${driver.name}" />
<br /><br />


<label for="${AttributeConst.DRI_TEL.getValue()}">電話番号</label><br />
<input type="text" name="${AttributeConst.DRI_TEL.getValue()}" id="${AttributeConst.DRI_TEL.getValue()}" value="${driver.tel}" />
<br /><br />

<label for="${AttributeConst.DRI_TEXT.getValue()}">テキスト</label><br />
<input type="text" name="${AttributeConst.DRI_TEXT.getValue()}" id="${AttributeConst.DRI_TEXT.getValue()}" />
<br /><br />

<br /><br />
<input type="hidden" name="${AttributeConst.DRI_ID.getValue()}" value="${driver.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>