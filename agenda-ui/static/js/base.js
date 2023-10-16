

$(document).ready(function(){
  $(":input").inputmask();
 /*
 or    Inputmask().mask(document.querySelectorAll("input"));*/
});


// **************************************************
// Controla os inputs de horarios de estagio
// **************************************************
$(document).ready(function () {

  const control_group_dia_semana = $('.dia-semana-control')

  control_group_dia_semana.on('change', function (){
    control_group_dia_semana.each(function (){
      const checked = $(this).is(':checked')
      const id = $(this).attr('id')
      const inputs = $('.'+id)

      inputs.each(function (){
        $(this).prop('disabled', !checked)
      });
    });
  });
});


// **************************************************
// Previne que formulario seja submetido invalido
// **************************************************
$(document).ready(function () {
  const forms = $('.needs-validation')

  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }

      form.classList.add('was-validated')
    }, false)
  })
});


$(document).ready(function () {
  const control_group_dia_semana = $('.dia-semana-control')

  control_group_dia_semana.each(function () {

    const checked = $(this).is(':checked')
    const id = $(this).attr('id')
    const inputs = $('.'+id)

    inputs.each(function (){
      $(this).prop('disabled', !checked)
    });
  });
});


