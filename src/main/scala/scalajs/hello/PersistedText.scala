package scalajs.hello

import org.scalajs.dom.window.localStorage

object PersistedText {
  def apply(localStorageKey: String, defaultValue: String = "") = new PersistedText(localStorageKey, defaultValue)
}

class PersistedText(localStorageKey: String, defaultValue: String) {
  private var currentValue = load().getOrElse {
    save(defaultValue)
    defaultValue
  }

  def apply(): String = currentValue

  def `:=`(newValue: String): Unit = {
    currentValue = newValue
    save(currentValue)
  }

  private def save(value: String): Unit = localStorage.setItem(localStorageKey, value)

  private def load(): Option[String] = Option {
    localStorage.getItem(localStorageKey)
  }
}