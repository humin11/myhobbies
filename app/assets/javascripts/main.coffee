
$ ->
    pathname = "/"+location.pathname.split("/")[1]
    $('ul.nav li.active').removeClass 'active'
    $('ul.nav a[href="'+pathname+'"]').parent('li').addClass 'active'
