$ -> 
	$('#create-public-community').bind 'click', (event) ->
		$('#form-public-community').show 'fast'
		$('#input-public-community').focus()
		$('#form-private-community').hide()
		
	$('#create-private-community').bind 'click', (event) ->
		$('#form-private-community').show 'fast'
		$('#input-private-community').focus()
		$('#form-public-community').hide ''
	