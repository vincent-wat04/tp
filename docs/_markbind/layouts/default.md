<frontmatter>
  title: ContactHelper
</frontmatter>

<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
  <div class="container">
    <a class="navbar-brand" href="{{ baseUrl }}/index.html">
      <!-- Project name / brand -->
      ContactHelper
    </a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">

        <!-- Project pages -->
        <li class="nav-item">
          <a class="nav-link" href="{{ baseUrl }}/UserGuide.html">User Guide</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="{{ baseUrl }}/DeveloperGuide.html">Developer Guide</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="{{ baseUrl }}/AboutUs.html">About Us</a>
        </li>

        <!-- Optional: link to contact page if you have one -->
        <!--
        <li class="nav-item">
          <a class="nav-link" href="{{ baseUrl }}/ContactUs.html">Contact</a>
        </li>
        -->

      </ul>

      <!-- Right-side links -->
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" target="_blank"
             href="https://github.com/AY2526S1-CS2103T-F15A-3/tp">
            GitHub
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" target="_blank"
             href="https://github.com/AY2526S1-CS2103T-F15A-3/tp/actions">
            Build Status
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" target="_blank"
             href="https://ay2526s1-cs2103t-f15a-3.github.io/tp/">
            Project Site
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<main class="page container mt-4 mb-5">
  <div class="row">
    <div class="col-lg-12">
      <div class="content">
        <placeholder id="content"/>
      </div>
    </div>
  </div>
</main>

<footer class="bg-light border-top py-3">
  <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center">
    <div class="mb-2 mb-md-0">
      <small>
        © {{ currentYear }} ContactHelper Team —
        <span>
          Based on SE-EDU’s
          <a target="_blank" href="https://se-education.org">AddressBook-Level3</a>
          (acknowledged).
        </span>
      </small>
    </div>
    <div>
      <small>
        <a target="_blank" href="https://github.com/AY2526S1-CS2103T-F15A-3/tp/actions/workflows/gradle.yml">
          CI
        </a> ·
        <a target="_blank" href="https://github.com/AY2526S1-CS2103T-F15A-3/tp">Repo</a> ·
        <a target="_blank" href="https://ay2526s1-cs2103t-f15a-3.github.io/tp/">Website</a>
      </small>
    </div>
  </div>
</footer>
