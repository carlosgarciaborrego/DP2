<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find owners</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarians</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'causes'}" url="/causes"
					title="causes">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Causes</span>
				</petclinic:menuItem>
				<security:authorize access="hasAuthority('veterinarian') or hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'clinic'}" url="/clinic"
					title="clinic">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Clinics</span>
				</petclinic:menuItem>
				</security:authorize>
				<security:authorize access="hasAuthority('veterinarian') or hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'hotels'}" url="/hotels"
					title="hotels">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Hotels</span>
				</petclinic:menuItem>
				</security:authorize>
					<security:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'providers'}" url="/providers"
					title="providers">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Providers</span>
				</petclinic:menuItem>
				</security:authorize>
				<security:authorize access="hasAuthority('owner') or hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'reservation'}" url="/reservations"
					title="reservations">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Reservations</span>
				</petclinic:menuItem>
				</security:authorize>
<%-- 				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>
				 --%>

			</ul>



			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											  <security:authorize access="hasAuthority('veterinarian')">
											<p class="text-left">
												<a href="<c:url value="/vet/profile" />"
													class="btn btn-primary btn-block btn-sm">Vet Profile</a>
											</p>
											   </security:authorize>
											<security:authorize access="hasAuthority('admin')">
											<p class="text-left">
												<a href="<c:url value="/dash" />"
													class="btn btn-primary btn-block btn-sm">Dashboard</a>
											</p>
											   </security:authorize>
										</div>
									</div>
								</div>
							</li>       
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
