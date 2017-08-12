<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
		<!-- 本页面涉及的js函数，都在head.jsp页面中  -->
		<div id="sidebar">

				<div id="sidebar-shortcuts">

					<div id="sidebar-shortcuts-large">

						<button class="btn btn-small btn-success" onclick="changeMenu();" title="切换菜单"><i class="icon-pencil"></i></button>

						<button class="btn btn-small btn-info" title="UI实例" onclick="window.open('<%=basePathl%>UI_new');"><i class="icon-eye-open"></i></button>

						<button class="btn btn-small btn-warning" title="数据字典" id="adminzidian" onclick="zidian();"><i class="icon-book"></i></button>
						
						<button class="btn btn-small btn-danger" title="菜单管理" id="adminmenu" onclick="menu();"><i class="icon-cogs"></i></button>
						
					</div>

					<div id="sidebar-shortcuts-mini">

						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>

					</div>

				</div><!-- #sidebar-shortcuts -->


				<ul class="nav nav-list">

					<c:if test="${pdm.isM1 != 'index' }"><li></c:if>
					<c:if test="${pdm.isM1 == 'index' }"><li class="active"></c:if>
					  <a href="login_index.do?&isM1=index"><i class="icon-dashboard"></i><span>后台首页</span></a>
					</li>

						<%--中孝账单记录管理系统--%>

			<c:forEach items="${menuList}" var="menu">
				<c:if test="${menu.hasMenu}">
				<c:if test="${pdm.isM1 != menu.MENU_ID }"><li></c:if>
				<c:if test="${pdm.isM1 == menu.MENU_ID }"><li class="active open"></c:if>
					  <a href="#" class="dropdown-toggle" >
						<i class="${menu.MENU_ICON == null ? 'icon-desktop' : menu.MENU_ICON}"></i>
						<span>${menu.MENU_NAME }</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
				
					<c:forEach items="${menu.subMenu}" var="sub">
						<c:if test="${sub.hasMenu}">
						<c:choose>
							<c:when test="${not empty sub.MENU_URL}">
							<c:if test="${pdm.isM2 != sub.MENU_ID }"><li></c:if>
							<c:if test="${pdm.isM2 == sub.MENU_ID }"><li class="active"></c:if>
							<a href="${sub.MENU_URL }&isM1=${menu.MENU_ID }&isM2=${sub.MENU_ID }"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:when>
							<c:otherwise>
							<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a></li>
							</c:otherwise>
						</c:choose>
						</c:if>
					</c:forEach>
				
				  </ul>
				</li>
				</c:if>
			</c:forEach>





					

				</ul><!--/.nav-list-->



				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>





			</div><!--/#sidebar-->

