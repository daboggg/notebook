export default function (value) {
  if (value.indexOf(' ') === -1 && value.length > 20) {
    return value.substring(0, 20) + '...'
  } else {
    return value
  }
}
